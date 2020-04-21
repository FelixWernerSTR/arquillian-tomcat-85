package de.fewe.arquillian;

import java.io.Serializable;
/**
 * 
 * Represents functionality called by @NumberServlet
 *
 */
public class DemoLib implements Serializable {
	private static final long serialVersionUID = -6750889810879586928L;
	private int num;

    public DemoLib() {
        this.num = 0;
    }

    public int getNum() {
        return num;
    }

    public int getNumAndIncrement() {
        int retVal = this.getNum();
        num++;
        return retVal;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
