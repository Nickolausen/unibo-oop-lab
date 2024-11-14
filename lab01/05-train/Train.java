public class Train {
    int nTotSeats;
    int nFirstClassSeats;
    int nSecondClassSeats;
    int nFirstClassReservedSeats;

    void build(int nFirstClassSeats, int nSecondClassSeats) 
    {
        this.nFirstClassSeats = nFirstClassSeats;
        this.nSecondClassSeats = nSecondClassSeats;
        this.nFirstClassReservedSeats = 0;

        this.nTotSeats = nFirstClassSeats + nSecondClassSeats + nFirstClassReservedSeats;
    }

    void reserveFirstClassSeats(int seats) 
    {
        this.nFirstClassReservedSeats += seats;
        this.nFirstClassSeats -= seats;
    }

    void reserveSecondClassSeats(int seats) 
    {
        this.nSecondClassSeats -= seats;
    }

    double getTotOccupancyRatio() 
    {
        return (double)((this.nFirstClassSeats + this.nSecondClassSeats) / this.nTotSeats) * 100; 
    }

    double getFirstClassOccupancyRatio() 
    {
        return (double)(this.nFirstClassReservedSeats / this.nFirstClassSeats) * 100;
    }

    // double getSecondClassOccupancyRatio() 
    // {
        
    // }
}
