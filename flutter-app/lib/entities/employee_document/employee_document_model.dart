import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../document_type/document_type_model.dart';
import '../employee/employee_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class EmployeeDocument {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'documentName')
  final String documentName;

  @JsonProperty(name: 'documentNumber')
  final String documentNumber;

  @JsonProperty(name: 'documentStatus', enumValues: EmployeeDocumentStatus.values)
  final EmployeeDocumentStatus documentStatus;

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

  @JsonProperty(name: 'documentType')
  final DocumentType documentType;

  @JsonProperty(name: 'employee')
  final Employee employee;

  @JsonProperty(name: 'approvedBy')
  final Employee approvedBy;

 const EmployeeDocument (
     this.id,
        this.documentName,
        this.documentNumber,
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
        this.documentType,
        this.employee,
        this.approvedBy,
    );

@override
String toString() {
    return 'EmployeeDocument{'+
    'id: $id,' +
        'documentName: $documentName,' +
        'documentNumber: $documentNumber,' +
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
        'documentType: $documentType,' +
        'employee: $employee,' +
        'approvedBy: $approvedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is EmployeeDocument &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: EmployeeDocumentStatus.values)
enum EmployeeDocumentStatus {
    EXPIRED ,
    ACTIVE ,
    ARCHIVED
}
