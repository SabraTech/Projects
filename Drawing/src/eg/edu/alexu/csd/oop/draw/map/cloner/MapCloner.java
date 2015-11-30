package eg.edu.alexu.csd.oop.draw.map.cloner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The Class CloneMap.
 */
public class MapCloner {
  private static MapCloner singleton;

  private MapCloner() {

  }

  public static MapCloner getInst() {
    if (singleton == null) {
      singleton = new MapCloner();
    }
    return singleton;
  }

  /**
   * Map Cloner clones any map.
   *
   * @param map
   * 
   * @return the clone of the map
   */
  public HashMap<String, Double> cloneMap(Map<String, Double> map) {
    HashMap<String, Double> ret = new HashMap<String, Double>();
    Iterator<String> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      String dummy = new String(iterator.next());
      if (map.get(dummy) == null) {
        ret.put(dummy, null);
      } else {
        ret.put(dummy, map.get(dummy).doubleValue());
      }

    }
    return ret;
  }
}
