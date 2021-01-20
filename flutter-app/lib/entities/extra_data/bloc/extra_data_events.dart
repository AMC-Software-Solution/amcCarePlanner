part of 'extra_data_bloc.dart';

abstract class ExtraDataEvent extends Equatable {
  const ExtraDataEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitExtraDataList extends ExtraDataEvent {}

class EntityNameChanged extends ExtraDataEvent {
  final String entityName;
  
  const EntityNameChanged({@required this.entityName});
  
  @override
  List<Object> get props => [entityName];
}
class EntityIdChanged extends ExtraDataEvent {
  final int entityId;
  
  const EntityIdChanged({@required this.entityId});
  
  @override
  List<Object> get props => [entityId];
}
class ExtraDataKeyChanged extends ExtraDataEvent {
  final String extraDataKey;
  
  const ExtraDataKeyChanged({@required this.extraDataKey});
  
  @override
  List<Object> get props => [extraDataKey];
}
class ExtraDataValueChanged extends ExtraDataEvent {
  final String extraDataValue;
  
  const ExtraDataValueChanged({@required this.extraDataValue});
  
  @override
  List<Object> get props => [extraDataValue];
}
class ExtraDataValueDataTypeChanged extends ExtraDataEvent {
  final DataType extraDataValueDataType;
  
  const ExtraDataValueDataTypeChanged({@required this.extraDataValueDataType});
  
  @override
  List<Object> get props => [extraDataValueDataType];
}
class ExtraDataDescriptionChanged extends ExtraDataEvent {
  final String extraDataDescription;
  
  const ExtraDataDescriptionChanged({@required this.extraDataDescription});
  
  @override
  List<Object> get props => [extraDataDescription];
}
class ExtraDataDateChanged extends ExtraDataEvent {
  final DateTime extraDataDate;
  
  const ExtraDataDateChanged({@required this.extraDataDate});
  
  @override
  List<Object> get props => [extraDataDate];
}
class HasExtraDataChanged extends ExtraDataEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ExtraDataFormSubmitted extends ExtraDataEvent {}

class LoadExtraDataByIdForEdit extends ExtraDataEvent {
  final int id;

  const LoadExtraDataByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteExtraDataById extends ExtraDataEvent {
  final int id;

  const DeleteExtraDataById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadExtraDataByIdForView extends ExtraDataEvent {
  final int id;

  const LoadExtraDataByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
