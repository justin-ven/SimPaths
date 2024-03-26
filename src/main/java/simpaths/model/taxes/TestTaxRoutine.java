package simpaths.model.taxes;

import simpaths.model.TaxEvaluation;

import java.io.File;
import java.io.IOException;

public class TestTaxRoutine {

    TestTaxRoutine(){}

    /**
     * METHOD TO EXTEND UKMOD INPUT DATA TO FILL GAPS
     */
    public static void extendDatabaseInput() {

        // compile data that identify database gaps
        MatchIndicesSet imperfectMatchIndices = screenImperfectMatches(false);


    }

    /**
     * METHOD TO SCREEN IMPERFECT DATABASE MATCHES REPORTED BY THE MODEL AND REPACKAGE DATA TO FILL GAPS
     */
    public static MatchIndicesSet screenImperfectMatches(boolean flagSave) {

        // read and screen
        Matches imperfectMatches = new Matches();
        String dir = "C:\\MyFiles\\99 DEV ENV\\JAS-MINE\\SimPaths\\output\\20240325215222\\grids";
        try {
            for (int aa=18; aa<131; aa++) {
                String filePath = dir + File.separator + "poor_match_age_" + aa + ".csv";
                File file = new File(filePath);
                if (file.exists())
                    imperfectMatches.read(filePath);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        // generate working variables for expanding tax database
        MatchIndicesSet matchIndicesSet = new MatchIndicesSet();
        KeyFunction keyFunction = new KeyFunction();
        for (Match match : imperfectMatches.getSet()) {

            MatchIndices indices = new MatchIndices(match.getCandidateID(), match.getTargetNormalisedOriginalIncome());
            for (MatchFeature feature : MatchFeature.values()) {
                indices.set(feature, keyFunction.getMatchFeatureIndex(feature, 0, match.getKey0()));
            }
            matchIndicesSet.add(indices);
        }

        // write set to CSV file for processing in Stata
        if (flagSave)
            matchIndicesSet.write(dir);

        // return
        return matchIndicesSet;
    }

    public static void run() {

        int year, age, adults, children04, children59, children1017, disability1, disability2, careProvision, key, matchRegime;
        long donorID;
        double hoursWork1, hoursWork2, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, disposableIncomePerMonth;
        TaxEvaluation evaluatedTransfers;

        // age: 18-44; 45-under spa; spa
        // adults: single; couple
        // children 04: 0, 1, 2+
        // children 59: 0, 1, 2+
        // children 1017: 0, 1+
        // hoursWork1/2: 0-5; 6-15; 16+
        // disability1/2: 0, 1
        // income: <946.17 <=2985.70, >2985.70

        year = 2025;
        age = 78;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        careProvision = 0;
        originalIncomePerMonth = 1500.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();


        year = 2025;
        age = 48;
        adults = 2;
        children04 = 1;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        careProvision = 0;
        originalIncomePerMonth = 4800.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 700.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 48;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 75;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        originalIncomePerMonth = 4500.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 1;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 500.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 38;
        adults = 2;
        children04 = 1;
        children59 = 1;
        children1017 = 2;
        hoursWork1 = 20.0;
        hoursWork2 = 16.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 2000.0;
        childcarePerMonth = 500.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 38;
        adults = 2;
        children04 = 1;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 10.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 2000.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 20.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 16.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 3000.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 1;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 10.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 1000.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 57;
        adults = 2;
        children04 = 0;
        children59 = 1;
        children1017 = 1;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 40;
        adults = 2;
        children04 = 1;
        children59 = 1;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 58;
        adults = 2;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 58;
        adults = 1;
        children04 = 0;
        children59 = 1;
        children1017 = 1;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 27;
        adults = 1;
        children04 = 0;
        children59 = 1;
        children1017 = 1;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 49;
        adults = 1;
        children04 = 1;
        children59 = 1;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 27;
        adults = 1;
        children04 = 1;
        children59 = 1;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 27;
        adults = 1;
        children04 = 0;
        children59 = 1;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 27;
        adults = 1;
        children04 = 3;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 48;
        adults = 1;
        children04 = 2;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 67;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 45;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2012;
        age = 37;
        adults = 2;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2015;
        age = 67;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2012;
        age = 46;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 35;
        adults = 1;
        children04 = 0;
        children59 = 0;
        children1017 = 0;
        hoursWork1 = 0.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 0.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        year = 2025;
        age = 35;
        adults = 1;
        children04 = 1;
        children59 = 1;
        children1017 = 0;
        hoursWork1 = 8.0;
        hoursWork2 = 0.0;
        disability1 = 0;
        disability2 = 0;
        originalIncomePerMonth = 320.0;
        secondIncomePerMonth = 0.0;
        childcarePerMonth = 0.0;
        evaluatedTransfers = new TaxEvaluation(year, age, adults, children04, children59, children1017, hoursWork1, hoursWork2, disability1, disability2, careProvision, originalIncomePerMonth, secondIncomePerMonth, childcarePerMonth, -2);
        key = evaluatedTransfers.getKeys().getKey(0);
        donorID = evaluatedTransfers.getImputedTransfers().getDonorID();
        disposableIncomePerMonth = evaluatedTransfers.getDisposableIncomePerMonth();
        matchRegime = evaluatedTransfers.getImputedTransfers().getMatchCriterion();

        int fin = 0;
    }
}
