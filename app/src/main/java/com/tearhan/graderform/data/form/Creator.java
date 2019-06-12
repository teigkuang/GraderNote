package com.tearhan.graderform.data.form;

/**
 * Creator This class is a grading form creator use a StringBuilder to get the
 * information
 *
 * @author teigkuang@163.com
 */
public class Creator {
    DataDeal dataDeal;
    /**
     * 有多少个组
     */
    public int dGrade = 3;
    /**
     * 开始的数字是多少
     */
    public int beginNum = 1;
    /**
     * 递增的数是多少
     */
    public int plusVar = 1;
    /**
     * 从第几阶段开始
     */
    public int beginGrade = 1;

    /**
     * 无参构造
     */
    public Creator() {
        super();
    }

    /**
     * 含级别
     *
     * @param dGrade 级别
     */
    public Creator(int dGrade) {
        super();
        this.dGrade = dGrade;
    }

    /**
     * 含级别和开始的数目
     *
     * @param dGrade   级别数
     * @param beginNum 开始数目
     */
    public Creator(int dGrade, int beginNum) {
        super();
        this.dGrade = dGrade;
        this.beginNum = beginNum;
    }

    /**
     * @param dGrade   级别数
     * @param beginNum 开始数目
     * @param plusVar  增加数量
     */

    public Creator(int dGrade, int beginNum, int plusVar) {
        super();
        this.dGrade = dGrade;
        this.beginNum = beginNum;
        this.plusVar = plusVar;
    }

    /**
     * 全参构造
     *
     * @param dGrade     级别数
     * @param beginNum   开始数目
     * @param plusVar    增加数量
     * @param beginGrade 开始级别
     */
    public Creator(int dGrade, int beginNum, int plusVar, int beginGrade, DataDeal dataDeal) {
        super();
        this.dGrade = dGrade;
        this.beginNum = beginNum;
        this.plusVar = plusVar;
        this.beginGrade = beginGrade * 2 + 1;
        this.dataDeal = dataDeal;
    }

    /**
     * 获取进阶表
     *
     * @return
     */
    public void getGradingForm() {
        // 总数量
        int totalCount = 0;
        // 总组次
        int totalIndex = 0;

        for (int i = beginGrade - 1; i < dGrade; i++) {
            int iGrade = i / 2;
            // 级别
            for (int j = 0; j < iGrade + 1; j++) {

                for (int j2 = 0; j2 <= i; j2++) {
                    StringBuilder sb = new StringBuilder();
                    // 单组数量
                    int rowCount = 0;
                    // 组次
                    int rowIndex = j2 + 1;
                    for (int k = 0; k <= iGrade; k++) {
                        int iCount = beginNum + (k + j) * plusVar;

                        sb.append("(" + iCount + ")");
                        rowCount += iCount;
                    }
                    if (i % 2 == 0) {
                        for (int k = iGrade - 1; k >= 0; k--) {
                            int iCount = beginNum + (k + j) * plusVar;

                            sb.append("(" + iCount + ")");
                            rowCount += iCount;
                        }
                    } else {
                        for (int k = iGrade; k >= 0; k--) {
                            int iCount = beginNum + (k + j) * plusVar;

                            sb.append("(" + iCount + ")");
                            rowCount += iCount;
                        }

                    }
                    totalCount += rowCount;
                    totalIndex += 1;

                    sb.append("  [" + rowCount + ":" + totalCount + "][" + rowIndex + ":" + totalIndex + "]\r\n");
                    dataDeal.saveData(sb.toString());
                }
            }

        }
    }

    public int getdGrade() {
        return dGrade;
    }

    public void setdGrade(int dGrade) {
        this.dGrade = dGrade;
    }

    public int getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(int beginNum) {
        this.beginNum = beginNum;
    }

    public int getPlusVar() {
        return plusVar;
    }

    public void setPlusVar(int plusVar) {
        this.plusVar = plusVar;
    }

    public int getBeginGrade() {
        return beginGrade;
    }

    public void setBeginGrade(int beginGrade) {
        this.beginGrade = beginGrade;
    }
}
