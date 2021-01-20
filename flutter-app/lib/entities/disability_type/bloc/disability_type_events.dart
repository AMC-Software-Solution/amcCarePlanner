part of 'disability_type_bloc.dart';

abstract class DisabilityTypeEvent extends Equatable {
  const DisabilityTypeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitDisabilityTypeList extends DisabilityTypeEvent {}

class DisabilityChanged extends DisabilityTypeEvent {
  final String disability;
  
  const DisabilityChanged({@required this.disability});
  
  @override
  List<Object> get props => [disability];
}
class DescriptionChanged extends DisabilityTypeEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class HasExtraDataChanged extends DisabilityTypeEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class DisabilityTypeFormSubmitted extends DisabilityTypeEvent {}

class LoadDisabilityTypeByIdForEdit extends DisabilityTypeEvent {
  final int id;

  const LoadDisabilityTypeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteDisabilityTypeById extends DisabilityTypeEvent {
  final int id;

  const DeleteDisabilityTypeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadDisabilityTypeByIdForView extends DisabilityTypeEvent {
  final int id;

  const LoadDisabilityTypeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
