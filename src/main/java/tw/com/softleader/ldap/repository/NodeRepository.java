package tw.com.softleader.ldap.repository;

import java.util.List;
import java.util.Objects;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import tw.com.softleader.ldap.entity.Node;
import tw.com.softleader.util.StringUtils;

@Component
public class NodeRepository implements GenericRepository {

  @Autowired
  private LdapTemplate ldapTemplate;

  public List<String> getAllNodes() {
    return ldapTemplate.search(LdapQueryBuilder.query().where("objectclass").is("organization"),
        new AttributesMapper<String>() {
          public String mapFromAttributes(Attributes attrs) throws NamingException {
            return attrs.get("o").get().toString();
          }
        });
  }

  public List<String> getNode(String[]... nodes) {
    ContainerCriteria criteria = LdapQueryBuilder.query().where("dc").is("example");
    for (String[] node : nodes) {
      Objects.requireNonNull(node[0]);
      Objects.requireNonNull(node[1]);
      criteria.and(node[0]).is(node[1]);
    }
    
    return ldapTemplate.search(criteria,
        new AttributesMapper<String>() {
          public String mapFromAttributes(Attributes attrs) throws NamingException {
            return attrs.get("o").get().toString();
          }
        });
  }

  public void create(Node node) throws InvalidNameException {
    DirContextAdapter context = new DirContextAdapter(buildDn(node));
    mapToContext(node, context);
    ldapTemplate.bind(context);
  }
  
  protected void mapToContext(Node node, DirContextOperations context) throws InvalidNameException {
    context.setAttributeValues("objectclass", new String[] {"top", "organization"});
    Name dn = context.getDn();
    if (StringUtils.isNotEmpty(node.getParentName())) {
      dn.add("o="+node.getParentName());
    }
    dn.add("o="+node.getName());
    context.setDn(dn);
 }
  
  protected Name buildDn(Node node) {
    return LdapNameBuilder.newInstance()
        .build();
 }

}
