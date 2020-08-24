package com.github.mrkinghh.designpattern;

import java.util.Observable;
import java.util.Observer;

public class ObserverPattern {
    public static void main(String[] args) {
        ConcreteObserver1 concreteObserver1 = new ConcreteObserver1();
        ConcreteObserver2 concreteObserver2 = new ConcreteObserver2();
        ConcertSubject concertSubject = new ConcertSubject();
        concertSubject.addObserver(concreteObserver1);
        concertSubject.addObserver(concreteObserver2);
        concertSubject.change();
    }
}

class ConcreteObserver1 implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ConcreteObserver1接收到更新");
    }
}

class ConcreteObserver2 implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ConcreteObserver2接收到更新");
    }
}

class ConcertSubject extends Observable {
    public void change() {
        setChanged();
        this.notifyObservers();
    }
}
