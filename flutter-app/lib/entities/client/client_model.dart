import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class Client {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'clientName')
  final String clientName;

  @JsonProperty(name: 'clientDescription')
  final String clientDescription;

  @JsonProperty(name: 'clientLogoUrl')
  final String clientLogoUrl;

  @JsonProperty(name: 'clientContactName')
  final String clientContactName;

  @JsonProperty(name: 'clientPhone')
  final String clientPhone;

  @JsonProperty(name: 'clientEmail')
  final String clientEmail;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'enabled')
  final bool enabled;

  @JsonProperty(name: 'reason')
  final String reason;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const Client (
     this.id,
        this.clientName,
        this.clientDescription,
        this.clientLogoUrl,
        this.clientContactName,
        this.clientPhone,
        this.clientEmail,
        this.createdDate,
        this.enabled,
        this.reason,
        this.lastUpdatedDate,
        this.hasExtraData,
    );

@override
String toString() {
    return 'Client{'+
    'id: $id,' +
        'clientName: $clientName,' +
        'clientDescription: $clientDescription,' +
        'clientLogoUrl: $clientLogoUrl,' +
        'clientContactName: $clientContactName,' +
        'clientPhone: $clientPhone,' +
        'clientEmail: $clientEmail,' +
        'createdDate: $createdDate,' +
        'enabled: $enabled,' +
        'reason: $reason,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Client &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


