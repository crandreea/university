#include <iostream>
#include <exception>
using namespace std;
using std::string;
using std::exception;

class Channel{
public:
    Channel() = default;
    virtual void send(string& msg) = 0;

};

class Contact{
private:
    unique_ptr<Channel> c1;
    unique_ptr<Channel> c2;
    unique_ptr<Channel> c3;

public:
    Contact(unique_ptr<Channel> c11, unique_ptr<Channel> c21, unique_ptr<Channel> c31) : c1{move(c11)}, c2{move(c21)}, c3{move(c31)}{}

    void sendAlarm(string& msg){
        int sw = 1;
        while(sw){
            try{
                c1->send(msg);
                sw = 0;
                c2->send(msg);
                sw = 0;
                c3->send(msg);
                sw = 0;
            }
            catch(exception& e){
                sw = 1;
            }
        }

    }
};

class Failover : public Channel{
private:
    unique_ptr<Channel> c1;
    unique_ptr<Channel> c2;
public:
    Failover(unique_ptr<Channel> c11, unique_ptr<Channel> c21) : c1{move(c11)}, c2{move(c21)}{}
    void send(string& msg) override{
        try{
            c1->send(msg);
        }
        catch (exception& e){
            c2->send(msg);
        }
    }
};

class Telefon : public Channel{
private:
    int nrTel;
public:
    Telefon(int nrTel): nrTel{nrTel}{}

    void send(string& msg) override{
        srand(time(0));
        string number;
        for(int i = 0; i<10; i++)
            number+=to_string(rand()%10);
        if(to_string(nrTel) == number)
            throw invalid_argument("linie ocupata, incearca");
        else
        {
            msg = "dial: ";
            cout<< msg <<" "<<nrTel;
        }


    }
};

class Fax : public Telefon{
public:
    Fax(int nrTel): Telefon{nrTel}{}
    void send(string& msg) override{
        try{
            Telefon::send(msg);
            msg = "sending fax";
            cout<<msg<<"endl";
        }
        catch (exception& e){
        }
    }
};

class SMS : public Telefon{
public:
    SMS(int nrTel): Telefon{nrTel}{}
    void send(string& msg) override{
        try{
            Telefon::send(msg);
            msg = "sending sms";
            cout<<msg<<"endl";
        }
        catch (exception& e){
        }
    }
};

Contact f(){
    auto t1 = make_unique<Telefon>(5567253718);
    auto f1 = make_unique<Fax>(5567253718);
    auto s1 = make_unique<SMS>(5567253718);

    auto t2 = make_unique<Failover>(move(f1), move(s1));
    auto t3 = make_unique<Failover>(move(t1), move(t2));

    Contact c(move(t1), move(t2), move(t3));

    return c;

}
int main() {
    Contact c = f();

    return 0;
}
