package references;

import java.util.HashMap;
import java.util.Map;

import models.Department;

public class MapaTeste {

		public static void main(String[] args) {
			Map<Integer,Department> map = new HashMap<Integer,Department>();
			
			map.put(1, new Department(1,"Student"));
			Department dep = map.get(1);
			
			System.out.println(dep);
		}
}
