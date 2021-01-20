import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class DocumentType {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'documentTypeTitle')
  final String documentTypeTitle;

  @JsonProperty(name: 'documentTypeDescription')
  final String documentTypeDescription;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const DocumentType (
     this.id,
        this.documentTypeTitle,
        this.documentTypeDescription,
        this.createdDate,
        this.lastUpdatedDate,
        this.hasExtraData,
    );

@override
String toString() {
    return 'DocumentType{'+
    'id: $id,' +
        'documentTypeTitle: $documentTypeTitle,' +
        'documentTypeDescription: $documentTypeDescription,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is DocumentType &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


