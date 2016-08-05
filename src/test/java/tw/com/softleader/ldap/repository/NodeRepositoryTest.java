package tw.com.softleader.ldap.repository;

import java.util.List;

import javax.naming.InvalidNameException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import tw.com.softleader.ldap.GenericTest;

public class NodeRepositoryTest extends GenericTest {
  
  @Autowired
  private NodeRepository nodeRepository;

  @Test
  public void test() throws InvalidNameException {
//    Node node = new Node();
//    node.setParentName("softleader");
//    node.setName("rhys");
//    nodeRepository.create(node);
    
    List<String> nodes = nodeRepository.getNode(new String[]{"o", "tw"}, new String[]{"o", "softleader"});
    nodes.forEach(System.out::println);
  }

}
