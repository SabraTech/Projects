package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class HashTableDoubleHashing<K,V> implements IHash<K,V>, IHashDouble {

  private int M;
  private int size;
  private int collisions;
  private int R;
  private ArrayList<Pair<K,V>> table;
  
  
  public HashTableDoubleHashing(){
    size = 0;
    M = 1200;
    collisions = 0;
    R = largestPrime(M);
    table = new ArrayList<Pair<K,V>>();
    for(int i=0;i<1200;i++){
      table.add(null);
    }
  }
  
  private int largestPrime(int target){
    for(int i = target-1;i>0;i--){
      boolean prime = true;
      for(int j = 2;j*j <= i&&prime;j++){
        if(i%j == 0){
          prime = false;
        }
      }
      if(prime){
        return i;
      }
    }
    return 0;
  }
  
  private int hash(K key){
    return (key.hashCode() & 0x7fffffff) % M;
  }
  
  private int hash2(K key){
    return R - ((Integer)key % R);
  }
  
  @Override
  public void put(K key, V value) {
    int h = hash(key);
    int h2 = hash2(key);
    int tmp = h;
    
    for(int i=0;table.get(h)!= null && !table.get(h).getKey().equals(-1);i++,h = (h+h2)%M){
      if(i == M){
        reHash();
      }
    }
    
    for(;table.get(tmp)!=null&& !table.get(tmp).getKey().equals(-1);tmp = (tmp+h2)&M){
      collisions++;
    }
  }
  
  private void reHash(){
    
    R = largestPrime(M*2);
    
    ArrayList<Pair<K,V>> tmp = new ArrayList<Pair<K,V>>();
    for(int i=0;i<M;i++){
      tmp.add(null);
    }
    M = M*2;
    size = 0;
    int count = 0;
    
    for(Pair<K,V> p : table){
      tmp.set(count++, p);
    }
    
    table = new ArrayList<Pair<K,V>>();
    for(int i=0;i<M;i++){
      table.add(null);
    }
    
    for(Pair<K,V> p : tmp){
      if(p != null){
        put(p.getKey(),p.getValue());
      }
    }
  }

  @Override
  public String get(K key) {
    int h = hash(key);
    int h2 = hash(key);
    
    for(;table.get(h)!=null;h=(int)(h+h2)%M){
      if(table.get(h).getKey().equals(key)){
        return (String) table.get(h).getValue();
      }
    }
    return null;
  }

  @Override
  public void delete(K key) {
    int h = hash(key);
    int h2 = hash(key);
    
    for(;table.get(h)!=null;h=(int)(h+h2)%M){
      if(table.get(h).getKey().equals(key)){
        table.set(h, new Pair<K,V>((K)new Integer(-1),table.get(h).getValue()));
        size++;
        return;
      }
    }
    
  }

  @Override
  public boolean contains(K key) {
    return get(key)!=null;
  }

  @Override
  public boolean isEmpty() {
    return size==0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public int capacity() {
    return M;
  }

  @Override
  public int collisions() {
    return collisions;
  }

  @Override
  public Iterable<K> keys() {
    ArrayList<K> keys = new ArrayList<K>();
    for(Pair<K,V> tmp : table){
      keys.add(tmp.getKey());
    }
    return keys;
  }
  
}
