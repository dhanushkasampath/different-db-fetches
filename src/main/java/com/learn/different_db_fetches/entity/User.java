package com.learn.different_db_fetches.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@NamedStoredProcedureQuery( // below code is needed for fetching data via stored procedure
    name = "User.getByAge",
    procedureName = "get_username_by_age",
    resultSetMappings = "UserNameDtoMapping",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "age", type = Integer.class)
    }
)
@SqlResultSetMapping(
    name = "UserNameDtoMapping",
    columns = {
        @ColumnResult(name = "first_name", type = String.class),
        @ColumnResult(name = "last_name", type = String.class)
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// db takes care of the id.
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer age;

    @Column
    private String city;

}
