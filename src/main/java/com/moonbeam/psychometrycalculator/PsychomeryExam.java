package com.moonbeam.psychometrycalculator;

import com.moonbeam.psychometrycalculator.PsychometryFormulas;


public class PsychomeryExam {


    private int quanSections, quanAns, verbalSections, verbalAns, engSections, engAns;

    private int verbalScore, quanScore, engScore;

    public PsychomeryExam(){
        quanSections = 0;
        quanAns = 0;
        verbalSections = 0;
        verbalAns = 0;
        engAns = 0;
        engSections = 0;
    }

    public PsychomeryExam setQuanAns(int quanAns) {
        this.quanAns = quanAns;
        return this;

    }

    public PsychomeryExam setEngAns(int engAns) {
        this.engAns = engAns;
        return this;

    }

    public PsychomeryExam setEngSections(int engSections) {
        this.engSections = engSections;
        return this;

    }

    public PsychomeryExam setQuanSections(int quanSections) {
        this.quanSections = quanSections;
        return this;

    }

    public PsychomeryExam setVerbalAns(int verbalAns) {
        this.verbalAns = verbalAns;
        return this;

    }

    public PsychomeryExam setVerbalSections(int verbalSections) {
        this.verbalSections = verbalSections;
        return this;
    }

    public int getEngSections() {
        return engSections;
    }

    public int getQuanSections() {
        return quanSections;
    }

    public int getVerbalSections() {
        return verbalSections;
    }

    public int getEngAns() {
        return engAns;
    }

    public int getQuanAns() {
        return quanAns;
    }

    public int getVerbalAns() {
        return verbalAns;
    }

    public int getVerbalScore(){
        return PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.VERBAL, verbalSections, verbalAns);
    }

    public int getEngScore() {
        return PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.ENG, engSections, engAns);
    }

    public int getQuanScore() {
        return PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.QUAN, quanSections, quanAns);
    }

    public PsychometryFormulas.Range calculateGeneralScore(int quanScore, int verbalScore, int engScore) {
        calculateSectionGrades();
        return PsychometryFormulas.getMultiDomainScore(this.quanScore, this.verbalScore, this.engScore);
    }

    public PsychometryFormulas.Range calculateHumanitiesScore() {
        calculateSectionGrades();
        return PsychometryFormulas.getHumanitiesWeightedScore(quanScore, verbalScore, engScore);
    }

    public PsychometryFormulas.Range calculateSciencesScore() {
        calculateSectionGrades();
        return PsychometryFormulas.getSciencesWeightedScore(quanScore, verbalScore, engScore);
    }

    private void calculateSectionGrades() {
        quanScore = PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.QUAN, quanSections, quanAns);
        verbalScore = PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.VERBAL, verbalSections, verbalAns);
        engScore = PsychometryFormulas.getSectionScore(PsychometryFormulas.Section.ENG, engSections, engAns);
    }

    public int calculateWeightedGrade() {
        calculateSectionGrades();
        return PsychometryFormulas.calculateWeightedGrade(this.quanScore, this.verbalScore, this.engScore);
    }
}
