class ComplexNum {
    /*
     * Inserire qui la dichiarazione dei campi della classe
     */
    double re;
    double im;

    void build(double re, double im) {
        this.re = re;
        this.im = im;
    }

    boolean equal(ComplexNum num) {
        if (num == null) return false;

        return (this.re == num.re && this.im == num.im);
    }

    void add(ComplexNum num) {
        /*
         * Implementare il metodo in modo che venga aggiunto il numero complesso
         * passato in input - Rif. Appendice A1 (slide)
         */
         num.im += this.im;
         num.re += this.re;
    }

    String toStringRep() {
        /*
         * Implementare il metodo in modo che restituisca una rappresentazione
         * testuale del numero complesso
         */
        if (this.im == 0) return "" + this.re;
        return this.im < 0 ? 
            this.re + "" + this.im + "i" : 
            this.re + "+" + this.im + "i";
        }
    }