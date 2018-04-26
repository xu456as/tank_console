package me.fetonxu.tank_console.service;

import io.netty.util.internal.ConcurrentSet;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class MachinePortService {
    private Map<String, ConcurrentLinkedDeque<Integer>> machines = new HashMap<>();

    public void registerMachine(String ip){
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<Integer>();
        for(int i = 9001; i< (1 << 15) ;++i){
            deque.push(i);
        }
        machines.put(ip, deque);
    }

    public int takePort(String ip){
        return machines.get(ip).poll();
    }
    public void recyclePort(String ip, int port){
        machines.get(ip).push(port);
    }
}
