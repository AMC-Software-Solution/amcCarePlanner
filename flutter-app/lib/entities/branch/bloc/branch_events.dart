part of 'branch_bloc.dart';

abstract class BranchEvent extends Equatable {
  const BranchEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitBranchList extends BranchEvent {}

class NameChanged extends BranchEvent {
  final String name;
  
  const NameChanged({@required this.name});
  
  @override
  List<Object> get props => [name];
}
class AddressChanged extends BranchEvent {
  final String address;
  
  const AddressChanged({@required this.address});
  
  @override
  List<Object> get props => [address];
}
class TelephoneChanged extends BranchEvent {
  final String telephone;
  
  const TelephoneChanged({@required this.telephone});
  
  @override
  List<Object> get props => [telephone];
}
class ContactNameChanged extends BranchEvent {
  final String contactName;
  
  const ContactNameChanged({@required this.contactName});
  
  @override
  List<Object> get props => [contactName];
}
class BranchEmailChanged extends BranchEvent {
  final String branchEmail;
  
  const BranchEmailChanged({@required this.branchEmail});
  
  @override
  List<Object> get props => [branchEmail];
}
class PhotoUrlChanged extends BranchEvent {
  final String photoUrl;
  
  const PhotoUrlChanged({@required this.photoUrl});
  
  @override
  List<Object> get props => [photoUrl];
}
class CreatedDateChanged extends BranchEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends BranchEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class HasExtraDataChanged extends BranchEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class BranchFormSubmitted extends BranchEvent {}

class LoadBranchByIdForEdit extends BranchEvent {
  final int id;

  const LoadBranchByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteBranchById extends BranchEvent {
  final int id;

  const DeleteBranchById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadBranchByIdForView extends BranchEvent {
  final int id;

  const LoadBranchByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
