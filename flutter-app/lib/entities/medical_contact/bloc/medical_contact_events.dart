part of 'medical_contact_bloc.dart';

abstract class MedicalContactEvent extends Equatable {
  const MedicalContactEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitMedicalContactList extends MedicalContactEvent {}

class DoctorNameChanged extends MedicalContactEvent {
  final String doctorName;
  
  const DoctorNameChanged({@required this.doctorName});
  
  @override
  List<Object> get props => [doctorName];
}
class DoctorSurgeryChanged extends MedicalContactEvent {
  final String doctorSurgery;
  
  const DoctorSurgeryChanged({@required this.doctorSurgery});
  
  @override
  List<Object> get props => [doctorSurgery];
}
class DoctorAddressChanged extends MedicalContactEvent {
  final String doctorAddress;
  
  const DoctorAddressChanged({@required this.doctorAddress});
  
  @override
  List<Object> get props => [doctorAddress];
}
class DoctorPhoneChanged extends MedicalContactEvent {
  final String doctorPhone;
  
  const DoctorPhoneChanged({@required this.doctorPhone});
  
  @override
  List<Object> get props => [doctorPhone];
}
class LastVisitedDoctorChanged extends MedicalContactEvent {
  final DateTime lastVisitedDoctor;
  
  const LastVisitedDoctorChanged({@required this.lastVisitedDoctor});
  
  @override
  List<Object> get props => [lastVisitedDoctor];
}
class DistrictNurseNameChanged extends MedicalContactEvent {
  final String districtNurseName;
  
  const DistrictNurseNameChanged({@required this.districtNurseName});
  
  @override
  List<Object> get props => [districtNurseName];
}
class DistrictNursePhoneChanged extends MedicalContactEvent {
  final String districtNursePhone;
  
  const DistrictNursePhoneChanged({@required this.districtNursePhone});
  
  @override
  List<Object> get props => [districtNursePhone];
}
class CareManagerNameChanged extends MedicalContactEvent {
  final String careManagerName;
  
  const CareManagerNameChanged({@required this.careManagerName});
  
  @override
  List<Object> get props => [careManagerName];
}
class CareManagerPhoneChanged extends MedicalContactEvent {
  final String careManagerPhone;
  
  const CareManagerPhoneChanged({@required this.careManagerPhone});
  
  @override
  List<Object> get props => [careManagerPhone];
}
class CreatedDateChanged extends MedicalContactEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends MedicalContactEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends MedicalContactEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends MedicalContactEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class MedicalContactFormSubmitted extends MedicalContactEvent {}

class LoadMedicalContactByIdForEdit extends MedicalContactEvent {
  final int id;

  const LoadMedicalContactByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteMedicalContactById extends MedicalContactEvent {
  final int id;

  const DeleteMedicalContactById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadMedicalContactByIdForView extends MedicalContactEvent {
  final int id;

  const LoadMedicalContactByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
