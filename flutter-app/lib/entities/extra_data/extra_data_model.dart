import 'package:dart_json_mapper/dart_json_mapper.dart';


@jsonSerializable
class ExtraData {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'entityName')
  final String entityName;

  @JsonProperty(name: 'entityId')
  final int entityId;

  @JsonProperty(name: 'extraDataKey')
  final String extraDataKey;

  @JsonProperty(name: 'extraDataValue')
  final String extraDataValue;

  @JsonProperty(name: 'extraDataValueDataType', enumValues: DataType.values)
  final DataType extraDataValueDataType;

  @JsonProperty(name: 'extraDataDescription')
  final String extraDataDescription;

  @JsonProperty(name: 'extraDataDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime extraDataDate;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;
        
 const ExtraData (
     this.id,
        this.entityName,
        this.entityId,
        this.extraDataKey,
        this.extraDataValue,
        this.extraDataValueDataType,
        this.extraDataDescription,
        this.extraDataDate,
        this.hasExtraData,
    );

@override
String toString() {
    return 'ExtraData{'+
    'id: $id,' +
        'entityName: $entityName,' +
        'entityId: $entityId,' +
        'extraDataKey: $extraDataKey,' +
        'extraDataValue: $extraDataValue,' +
        'extraDataValueDataType: $extraDataValueDataType,' +
        'extraDataDescription: $extraDataDescription,' +
        'extraDataDate: $extraDataDate,' +
        'hasExtraData: $hasExtraData,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is ExtraData &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: DataType.values)
enum DataType {
    STRING ,
    BOOLEAN ,
    NUMBER ,
    DATE ,
    FILE ,
    OBJECT ,
    ARRAY ,
    GEO_POINT 
}