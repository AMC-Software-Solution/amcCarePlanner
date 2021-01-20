import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class SystemSetting {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'fieldName')
  final String fieldName;

  @JsonProperty(name: 'fieldValue')
  final String fieldValue;

  @JsonProperty(name: 'defaultValue')
  final String defaultValue;

  @JsonProperty(name: 'settingEnabled')
  final bool settingEnabled;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'updatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime updatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const SystemSetting (
     this.id,
        this.fieldName,
        this.fieldValue,
        this.defaultValue,
        this.settingEnabled,
        this.createdDate,
        this.updatedDate,
        this.clientId,
        this.hasExtraData,
    );

@override
String toString() {
    return 'SystemSetting{'+
    'id: $id,' +
        'fieldName: $fieldName,' +
        'fieldValue: $fieldValue,' +
        'defaultValue: $defaultValue,' +
        'settingEnabled: $settingEnabled,' +
        'createdDate: $createdDate,' +
        'updatedDate: $updatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is SystemSetting &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


