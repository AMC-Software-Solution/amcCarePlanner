import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_order/service_order_model.dart';
import '../service_user/service_user_model.dart';

@jsonSerializable
class Invoice {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'totalAmount')
  final String totalAmount;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'invoiceNumber')
  final String invoiceNumber;

  @JsonProperty(name: 'generatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime generatedDate;

  @JsonProperty(name: 'dueDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime dueDate;

  @JsonProperty(name: 'paymentDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime paymentDate;

  @JsonProperty(name: 'invoiceStatus', enumValues: InvoiceStatus.values)
  final InvoiceStatus invoiceStatus;

  @JsonProperty(name: 'tax')
  final String tax;

  @JsonProperty(name: 'attribute1')
  final String attribute1;

  @JsonProperty(name: 'attribute2')
  final String attribute2;

  @JsonProperty(name: 'attribute3')
  final String attribute3;

  @JsonProperty(name: 'attribute4')
  final String attribute4;

  @JsonProperty(name: 'attribute5')
  final String attribute5;

  @JsonProperty(name: 'attribute6')
  final String attribute6;

  @JsonProperty(name: 'attribute7')
  final String attribute7;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'serviceOrder')
  final ServiceOrder serviceOrder;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

 const Invoice (
     this.id,
        this.totalAmount,
        this.description,
        this.invoiceNumber,
        this.generatedDate,
        this.dueDate,
        this.paymentDate,
        this.invoiceStatus,
        this.tax,
        this.attribute1,
        this.attribute2,
        this.attribute3,
        this.attribute4,
        this.attribute5,
        this.attribute6,
        this.attribute7,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceOrder,
        this.serviceUser,
    );

@override
String toString() {
    return 'Invoice{'+
    'id: $id,' +
        'totalAmount: $totalAmount,' +
        'description: $description,' +
        'invoiceNumber: $invoiceNumber,' +
        'generatedDate: $generatedDate,' +
        'dueDate: $dueDate,' +
        'paymentDate: $paymentDate,' +
        'invoiceStatus: $invoiceStatus,' +
        'tax: $tax,' +
        'attribute1: $attribute1,' +
        'attribute2: $attribute2,' +
        'attribute3: $attribute3,' +
        'attribute4: $attribute4,' +
        'attribute5: $attribute5,' +
        'attribute6: $attribute6,' +
        'attribute7: $attribute7,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceOrder: $serviceOrder,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Invoice &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: InvoiceStatus.values)
enum InvoiceStatus {
    CREATED ,
    PAID ,
    CANCELLED
}
