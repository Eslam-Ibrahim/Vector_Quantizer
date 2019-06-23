/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vector_quantizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Eslam Ibrahim
 */
public class VectorQuantizer {

    public VectorQuantizer() {
    }

    // Private Methods
    private ArrayList<Vector> DivedeIntoVectors(ArrayList<Integer> input, int vectorRows) {
        ArrayList<Vector> result = new ArrayList<>();

        for (int i = 0; i < input.size(); i += vectorRows) {

            List tempList = input.subList(i, i + vectorRows);
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < vectorRows; j++) {
                temp.add((Integer) tempList.get(j));
            }
            result.add(new Vector(temp));

        }
        return result;
    }

    private Vector calculateAVG(ArrayList<Vector> input, int vectorRows) {
        if (input.isEmpty()) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < vectorRows; i++) {
                temp.add(0);
            }
            return new Vector(temp);
        }

        ArrayList<Integer> temp = new ArrayList<>();
        int sum = 0;
        int ctr = vectorRows;
        int index = 0;
        while (ctr > 0) {
            for (int i = 0; i < input.size(); i++) {   
                sum += input.get(i).getList().get(index);
            }
            temp.add(sum / input.size());
            sum = 0;
            index++;
            ctr--;
        }

        return new Vector(temp);
    }

    private ArrayList<ArrayList<Vector>> compareVectors(ArrayList<Vector> originalInput, ArrayList<Vector> comparingVectors, int VectRows) {
        ArrayList<ArrayList<Vector>> result = new ArrayList<ArrayList<Vector>>(comparingVectors.size());

        for (int i = 0; i < comparingVectors.size(); i++) {
            result.add(new ArrayList<Vector>());
        }
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> tempSorted = new ArrayList<>();
        for (int i = 0; i < originalInput.size(); i++) {
            for (int j = 0; j < comparingVectors.size(); j++) {
                // System.out.println("compareSize " +comparingVectors.size());
                int subsum = 0;
                for (int k = 0; k < VectRows; k++) {
                    subsum += (int) Math.pow(originalInput.get(i).getList().get(k) - comparingVectors.get(j).getList().get(k), 2);
                }
                temp.add(subsum);
                tempSorted.add(subsum);
            }
            // determine which group the vector will be added to
            Collections.sort(tempSorted);
            int index = temp.indexOf(tempSorted.get(0));
            result.get(index).add(originalInput.get(i));
            temp.clear();
            tempSorted.clear();

        }
        return result;

    }

    private ArrayList<Vector> Splitter(ArrayList<Vector> initialCodeBook, int vectRow) {
        ArrayList<Vector> initialCodeBookResult = new ArrayList<>();
        for (int i = 0; i < initialCodeBook.size(); i++) {
            int sum = 0;
            for (int j = 0; j < vectRow; j++) {
                sum += initialCodeBook.get(i).getList().get(j);
            }
            sum /= vectRow;
            ArrayList<Integer> subVect1 = new ArrayList<>();
            ArrayList<Integer> subVect2 = new ArrayList<>();
            for (int j = 0; j < vectRow; j++) {
                subVect1.add(sum - 1);
                subVect2.add(sum + 1);
            }
            initialCodeBookResult.add(new Vector(subVect1));
            initialCodeBookResult.add(new Vector(subVect2));
        }
        return initialCodeBookResult;
    }

    private ArrayList<Vector> ConstructCodeBook(ArrayList<Vector> originalInput, int codeBookLength, int vectRows) {
        ArrayList<Vector> codeBook = new ArrayList<>();
        ArrayList<ArrayList<Vector>> middleVectorsGroups = new ArrayList<ArrayList<Vector>>();
        // get first Code Book Vector
        codeBook.add(calculateAVG(originalInput, vectRows));
        // loop until you reach the # of levels needed
        int ctr = 0;
        while (codeBook.size() < codeBookLength) {
            System.out.println("Big Iteration" + ctr);
            // split the code book vectors
            codeBook = Splitter(codeBook, vectRows);

            // Grouping of original vectors
            //   middleVectorsGroups = compareVectors(originalInput, codeBook);
            // System.out.println("arraySize "+middleVectorsGroups.length);
            middleVectorsGroups = compareVectors(originalInput, codeBook, vectRows);
            // clear initial code book
            codeBook.clear();
            // Calculate AVG for each group
            for (int i = 0; i < middleVectorsGroups.size(); i++) {

                codeBook.add(calculateAVG(middleVectorsGroups.get(i), vectRows));
                //System.out.println("Iteration "+i);
            }
            ctr++;
            // groupingCtr*=2;
        }
        // Enhance the code book - loop until you get optimal Vectors
        int ctre = 0;
        while (true) {
            System.out.println("Bigenchancediteration " + ctre);
            ArrayList<Vector> temp = new ArrayList<>();
            // Grouping of original vectors
            middleVectorsGroups = compareVectors(originalInput, codeBook, vectRows);
            // Calculate AVG for each group - store it in temp
            for (int i = 0; i < middleVectorsGroups.size(); i++) {
                temp.add(calculateAVG(middleVectorsGroups.get(i), vectRows));
            }
            // compare temp with code book if they are equal --> i got optimal vectors
            //for (int i = 0; i < temp.size(); i++) {
            //   isOptimal = temp.get(i).equals(codeBook.get(i));
            // System.out.println(isOptimal);
            //}

            if (temp.equals(codeBook)) {
                break;
            }
            codeBook = temp;
            ctre++;
        }
        return codeBook;
    }

    private ArrayList<Integer> getLevelByVector(ArrayList<Vector> originalInput, ArrayList<Vector> codeBook, int vectRows) {
        ArrayList<Integer> levels = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> tempSorted = new ArrayList<>();
        for (int i = 0; i < originalInput.size(); i++) {
            for (int j = 0; j < codeBook.size(); j++) {
                // System.out.println("compareSize " +comparingVectors.size());
                int subsum = 0;
                for (int k = 0; k < vectRows; k++) {
                    subsum += (int) Math.pow(originalInput.get(i).getList().get(k) - codeBook.get(j).getList().get(k), 2);
                }
                temp.add(subsum);
                tempSorted.add(subsum);
            }
            // determine which group the vector will be added to
            Collections.sort(tempSorted);
            int index = temp.indexOf(tempSorted.get(0));
            levels.add(index);
            temp.clear();
            tempSorted.clear();

        }

        return levels;
    }
    // ************************************************************************************************************************ //

    //Public Methods
    public void Compression(String FileName, int codeBookLength, int vectRows) {
        ImgRW newReader = new ImgRW();
        ArrayList<Integer> Input = new ArrayList<>();
        ArrayList<Vector> codeBook = new ArrayList<>();
        ArrayList<Vector> InputVectors = new ArrayList<>();
        ArrayList<Integer> Output = new ArrayList<>();
        Input = newReader.readImage(FileName);
        InputVectors = DivedeIntoVectors(Input, vectRows);
        codeBook = ConstructCodeBook(InputVectors, codeBookLength, vectRows);
        Output = getLevelByVector(InputVectors, codeBook, vectRows);
        writeFile newFileWriter = new writeFile();
        newFileWriter.openFile("compressionOutput.txt");
        newFileWriter.writeArrayList(Output);
        newFileWriter.closeFile();
        // write codebook into file
        newFileWriter.openFile("CodeBook.txt");
        for (int i = 0; i < codeBook.size(); i++) {
            newFileWriter.writeArrayList(codeBook.get(i).getList());
        }
        newFileWriter.closeFile();
        newFileWriter.openFile("OriginalPexils.txt");
        newFileWriter.writeArrayList(Input);
        newFileWriter.closeFile();
    }

    public void Decompression(String fileName, int vectRows) {
        ImgRW newWriter = new ImgRW(512, 512);
        ArrayList<Integer> levels = new ArrayList<>();
        ArrayList<Integer> codeBookInt = new ArrayList<>();
        ArrayList<Vector> codeBookVectors = new ArrayList<>();
        ArrayList<Vector> decompOutputVectors = new ArrayList<>();
        ArrayList<Integer> decompOutputInt = new ArrayList<>();
        readFile newfileReader = new readFile();
        writeFile newfileWriter = new writeFile();
        newfileReader.openFile(fileName);
        levels = newfileReader.readArrayList();
        newfileReader.closeFile();
        // Read Code Book
        newfileReader.openFile("CodeBook.txt");
        codeBookInt = newfileReader.readArrayList();
        newfileReader.closeFile();
        // convert code book Integers into vectors 
        codeBookVectors = DivedeIntoVectors(codeBookInt, vectRows);
        // decompress
        for (int i = 0; i < levels.size(); i++) {
            decompOutputVectors.add(codeBookVectors.get(levels.get(i)));
        }
        // convert decompressed Output Vectors into integers
        for (int i = 0; i < decompOutputVectors.size(); i++) {
            for (int j = 0; j < vectRows; j++) {
                decompOutputInt.add(decompOutputVectors.get(i).getList().get(j));
            }
        }
        // write Img
        newWriter.writeImage(decompOutputInt, "Decompression.jpg");
        // write decompressed pixels
        newfileWriter.openFile("DecompressedPixels.txt");
        newfileWriter.writeArrayList(decompOutputInt);
        newfileWriter.closeFile();
    }

    public double calculateMSE() {
        double result = 0;
        double sum = 0;
        ArrayList<Integer> original = new ArrayList<>();
        ArrayList<Integer> decompressed = new ArrayList<>();
        readFile newfileReader = new readFile();
        newfileReader.openFile("OriginalPexils.txt");
        original = newfileReader.readArrayList();
        newfileReader.closeFile();
        newfileReader.openFile("DecompressedPixels.txt");
        decompressed = newfileReader.readArrayList();
        newfileReader.closeFile();
        for (int i = 0; i < original.size(); i++) {
            int subResult = (decompressed.get(i) - original.get(i));
            sum += Math.pow(subResult, 2);
        }
        result = (sum * 1.0) / original.size();
        return result;
    }
}
