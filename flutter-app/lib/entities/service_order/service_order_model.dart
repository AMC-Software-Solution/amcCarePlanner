import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../currency/currency_model.dart';

@jsonSerializable
class ServiceOrder {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'title')
  final String title;

  @JsonProperty(name: 'serviceDescription')
  final String serviceDescription;

  @JsonProperty(name: 'serviceHourlyRate')
  final String serviceHourlyRate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'currency')
  final Currency currency;
        
 const ServiceOrder (
     this.id,
        this.title,
        this.serviceDescription,
        this.serviceHourlyRate,
        this.clientId,
        this.createdDate,
        this.lastUpdatedDate,
        this.hasExtraData,
        this.currency,
    );

@override
String toString() {
    return 'ServiceOrder{'+
    'id: $id,' +
        'title: $title,' +
        'serviceDescription: $serviceDescription,' +
        'serviceHourlyRate: $serviceHourlyRate,' +
        'clientId: $clientId,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'hasExtraData: $hasExtraData,' +
        'currency: $currency,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is ServiceOrder &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


