package models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter@Setter
public class Student {
    private long id;
    private String name;
    private long age;
    private String email;
    public Student() {
    }

   public Student(long id,String name,long age,String email){
       this.id=id;
       this.name=name;
       this.age=age;
       this.email=email;

   }

}
