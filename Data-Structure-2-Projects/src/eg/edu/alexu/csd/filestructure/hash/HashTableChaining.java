package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class HashTableChaining<K, V> implements IHash<K, V>, IHashChaining {
  
  private int M = 1200;
  @SuppressWarnings("unchecked")
  private ArrayList<Pair<K, V>>[] table = (ArrayList<Pair<K, V>>[]) new ArrayList[1200];
  
  private int hash(K key) {
    return (key.hashCode() & 0x7fffffff) % M;
  }
  
  @Override
  public void put(K key, V value) {
    int i = hash(key);
    Pair<K,V> node = new Pair<K,V>(key, value);
    table[i].add(node);
  }
  
  @Override
  public String get(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table[i];
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        return (String) list.get(j).getValue();
      }
    }
    return null;
  }
  
  @Override
  public void delete(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table[i];
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        list.remove(j);
      }
    }
    
  }
  
  @Override
  public boolean contains(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table[i];
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public boolean isEmpty() {
    int cnt = 0;
    for(int i=0;i<table.length;i++){
      if(table[i].size() == 0){
        cnt++;
      }
    }
    if(cnt == 1200){
      return true;
    }else{
      return false;
    }
  }
  
  @Override
  public int size() {
    int cnt = 0;
    for(int i=0;i<table.length;i++){
      if(table[i].size() != 0){
        cnt++;
      }
    }
    return cnt;
  }
  
  @Override
  public int capacity() {
    return 1200;
  }
  
  @Override
  public int collisions() {
    int cnt = 0;
    for(int i=0;i<table.length;i++){
      if(table[i].size() > 1){
       cnt += table[i].size() - 1;
      }
    }
    return cnt;
  }
  
  @Override
  public Iterable<K> keys() {
    ArrayList<K> keys = new ArrayList<K>();
    for(int i=0;i<table.length;i++){
      for(int j=0;i<table[i].size();j++){
        keys.add(table[i].get(j).getKey());
      }
    }
    return keys;
  }
  
}
