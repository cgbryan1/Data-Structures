package assn02;

import java.util.Scanner;

public class JavaWarmUp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String[] categoriesList = {"phone", "laptop", "smart_watch"};

        int n = s.nextInt();
        // Date Time1 Category Fee Quantity Time2 AsmCost

        String[] date = new String[n];
        String[] time1 = new String[n];
        String[] category = new String[n];
        double[] fee = new double[n];
        int[] quantity = new int[n];
        double[] time2 = new double[n];
        double[] asmCost = new double[n];

        for (int i = 0; i < n; i++) {
            date[i] = s.next();
            time1[i] = s.next();
            category[i] = s.next();
            fee[i] = s.nextDouble();
            quantity[i] = s.nextInt();
            time2[i] = s.nextDouble();
            asmCost[i] = s.nextDouble();
        }

        // Find items with highest and lowest price per unit
        int highestItemIndex = getMaxPriceIndex(fee);
        int lowestItemIndex = getMinPriceIndex(fee);

        // highest priced info

        System.out.println(date[highestItemIndex]);
        System.out.println(time1[highestItemIndex]);
        System.out.println(category[highestItemIndex]);
        System.out.println(fee[highestItemIndex]);

        // lowest price info
        System.out.println(date[lowestItemIndex]);
        System.out.println(time1[lowestItemIndex]);
        System.out.println(category[lowestItemIndex]);
        System.out.println(fee[lowestItemIndex]);

        // Calculate the # of batches, total Fee, total Quantity, total Labor and Assembly costs by category.
        // Maintain following category-wise total stats in the following Category Arrays
        int[] numOfBatchesC = new int[categoriesList.length];// so numOfBatchesC[0] = # of batches in category categoriesList[0]
        double[] totFeeC = new double[categoriesList.length]; // total fee of each category = sum(fee * qty)
        int[] totQuantityC = new int[categoriesList.length];    // total qty of each category = sum (qty)
        double[] totLaborCostC = new double[categoriesList.length]; // total labor cost of each category = sum(time2 x 16)
        double[] totAsmCostC = new double[categoriesList.length]; // total Assembly cost of each category = sum(AsmCost)


        for (int i = 0; i < n; i++) {
            int catIndex = 0;
            // set the value of catIndex for each i to be such that category[i] == categoriesList[catIndex]
            while (!(categoriesList[catIndex].equals(category[i]))) {
                catIndex++;
                // catIndex will increase until it matches the category of the item at index i
            }
            // add the batches from this edition into the corresponding total for the category (each entry is one batch)
            numOfBatchesC[catIndex]++;
            totFeeC[catIndex] += (fee[i] * quantity[i]);
            totQuantityC[catIndex] = totQuantityC[catIndex] + quantity[i];
            totLaborCostC[catIndex] += (time2[i] * 16);
            totAsmCostC[catIndex] += asmCost[i];

        }

        for (int j = 0; j < categoriesList.length; j++) {
            if (numOfBatchesC[j] > 0) {
                System.out.println(categoriesList[j]);
                // print the total number of products assembled
                System.out.println(totQuantityC[j]);
                // the average assembling fee per unit
                System.out.printf("%.2f\n", totFeeC[j] / totQuantityC[j]);
                // and the average net profit per unit
                // total cost of entire category - assembly and labor costs divided by how many items
                System.out.printf("%.2f\n", (totFeeC[j] - (totAsmCostC[j] + totLaborCostC[j])) / totQuantityC[j]); // should i divide by quantity?
            }
        }
    }

    static int getMaxPriceIndex(double[] priceT) {
        int maxIdx = 0;
        for (int i = 0; i < priceT.length; i++) {
            if (priceT[i] >= priceT[maxIdx]) {
                maxIdx = i;
            }
        }
        return (maxIdx);
    }

    static int getMinPriceIndex(double[] priceT) {
        int minIdx = 0;
        for (int i = 0; i < priceT.length; i++) {
            if (priceT[i] <= priceT[minIdx]) {
                minIdx = i;
            }
        }
        return (minIdx);
    }
}