import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class EligibilityType {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'eligibilityType')
  final String eligibilityType;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const EligibilityType (
     this.id,
        this.eligibilityType,
        this.description,
        this.hasExtraData,
    );

@override
String toString() {
    return 'EligibilityType{'+
    'id: $id,' +
        'eligibilityType: $eligibilityType,' +
        'description: $description,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is EligibilityType &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


