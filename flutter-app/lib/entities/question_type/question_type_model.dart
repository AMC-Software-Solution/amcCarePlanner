import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class QuestionType {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'questionType')
  final String questionType;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;
        
 const QuestionType (
     this.id,
        this.questionType,
        this.lastUpdatedDate,
    );

@override
String toString() {
    return 'QuestionType{'+
    'id: $id,' +
        'questionType: $questionType,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is QuestionType &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


