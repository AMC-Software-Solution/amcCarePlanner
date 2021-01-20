part of 'eligibility_type_bloc.dart';

abstract class EligibilityTypeEvent extends Equatable {
  const EligibilityTypeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEligibilityTypeList extends EligibilityTypeEvent {}

class EligibilityTypeChanged extends EligibilityTypeEvent {
  final String eligibilityType;
  
  const EligibilityTypeChanged({@required this.eligibilityType});
  
  @override
  List<Object> get props => [eligibilityType];
}
class DescriptionChanged extends EligibilityTypeEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class HasExtraDataChanged extends EligibilityTypeEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EligibilityTypeFormSubmitted extends EligibilityTypeEvent {}

class LoadEligibilityTypeByIdForEdit extends EligibilityTypeEvent {
  final int id;

  const LoadEligibilityTypeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEligibilityTypeById extends EligibilityTypeEvent {
  final int id;

  const DeleteEligibilityTypeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEligibilityTypeByIdForView extends EligibilityTypeEvent {
  final int id;

  const LoadEligibilityTypeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
