import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class ClientDocument {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'documentName')
  final String documentName;

  @JsonProperty(name: 'documentNumber')
  final String documentNumber;

  @JsonProperty(name: 'documentType', enumValues: ClientDocumentType.values)
  final ClientDocumentType documentType;

  @JsonProperty(name: 'documentStatus', enumValues: DocumentStatus.values)
  final DocumentStatus documentStatus;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'issuedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime issuedDate;

  @JsonProperty(name: 'expiryDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime expiryDate;

  @JsonProperty(name: 'uploadedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime uploadedDate;

  @JsonProperty(name: 'documentFileUrl')
  final String documentFileUrl;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'uploadedBy')
  final Employee uploadedBy;

  @JsonProperty(name: 'approvedBy')
  final Employee approvedBy;
        
 const ClientDocument (
     this.id,
        this.documentName,
        this.documentNumber,
        this.documentType,
        this.documentStatus,
        this.note,
        this.issuedDate,
        this.expiryDate,
        this.uploadedDate,
        this.documentFileUrl,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.uploadedBy,
        this.approvedBy,
    );

@override
String toString() {
    return 'ClientDocument{'+
    'id: $id,' +
        'documentName: $documentName,' +
        'documentNumber: $documentNumber,' +
        'documentType: $documentType,' +
        'documentStatus: $documentStatus,' +
        'note: $note,' +
        'issuedDate: $issuedDate,' +
        'expiryDate: $expiryDate,' +
        'uploadedDate: $uploadedDate,' +
        'documentFileUrl: $documentFileUrl,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'uploadedBy: $uploadedBy,' +
        'approvedBy: $approvedBy,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is ClientDocument &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: ClientDocumentType.values)
enum ClientDocumentType {
    POLICY ,
    PROCEDURE ,
    FORM ,
    OTHER 
}@jsonSerializable
@Json(enumValues: DocumentStatus.values)
enum DocumentStatus {
    EXPIRED ,
    ACTIVE ,
    ARCHIVED 
}