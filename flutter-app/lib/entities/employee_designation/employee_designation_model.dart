import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class EmployeeDesignation {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'designation')
  final String designation;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'designationDate')
  final String designationDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const EmployeeDesignation (
     this.id,
        this.designation,
        this.description,
        this.designationDate,
        this.clientId,
        this.hasExtraData,
    );

@override
String toString() {
    return 'EmployeeDesignation{'+
    'id: $id,' +
        'designation: $designation,' +
        'description: $description,' +
        'designationDate: $designationDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is EmployeeDesignation &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


