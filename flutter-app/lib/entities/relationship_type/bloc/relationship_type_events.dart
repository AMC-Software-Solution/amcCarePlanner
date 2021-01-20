part of 'relationship_type_bloc.dart';

abstract class RelationshipTypeEvent extends Equatable {
  const RelationshipTypeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitRelationshipTypeList extends RelationshipTypeEvent {}

class RelationTypeChanged extends RelationshipTypeEvent {
  final String relationType;
  
  const RelationTypeChanged({@required this.relationType});
  
  @override
  List<Object> get props => [relationType];
}
class DescriptionChanged extends RelationshipTypeEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class ClientIdChanged extends RelationshipTypeEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends RelationshipTypeEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class RelationshipTypeFormSubmitted extends RelationshipTypeEvent {}

class LoadRelationshipTypeByIdForEdit extends RelationshipTypeEvent {
  final int id;

  const LoadRelationshipTypeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteRelationshipTypeById extends RelationshipTypeEvent {
  final int id;

  const DeleteRelationshipTypeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadRelationshipTypeByIdForView extends RelationshipTypeEvent {
  final int id;

  const LoadRelationshipTypeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
