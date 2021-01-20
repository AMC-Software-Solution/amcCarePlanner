part of 'employee_designation_bloc.dart';

enum EmployeeDesignationStatusUI {init, loading, error, done}
enum EmployeeDesignationDeleteStatus {ok, ko, none}

class EmployeeDesignationState extends Equatable {
  final List<EmployeeDesignation> employeeDesignations;
  final EmployeeDesignation loadedEmployeeDesignation;
  final bool editMode;
  final EmployeeDesignationDeleteStatus deleteStatus;
  final EmployeeDesignationStatusUI employeeDesignationStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DesignationInput designation;
  final DescriptionInput description;
  final DesignationDateInput designationDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EmployeeDesignationState(
{
    this.employeeDesignations = const [],
    this.employeeDesignationStatusUI = EmployeeDesignationStatusUI.init,
    this.loadedEmployeeDesignation = const EmployeeDesignation(0,'','','',0,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeDesignationDeleteStatus.none,
    this.designation = const DesignationInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.designationDate = const DesignationDateInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  });

  EmployeeDesignationState copyWith({
    List<EmployeeDesignation> employeeDesignations,
    EmployeeDesignationStatusUI employeeDesignationStatusUI,
    bool editMode,
    EmployeeDesignationDeleteStatus deleteStatus,
    EmployeeDesignation loadedEmployeeDesignation,
    FormzStatus formStatus,
    String generalNotificationKey,
    DesignationInput designation,
    DescriptionInput description,
    DesignationDateInput designationDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EmployeeDesignationState(
      employeeDesignations: employeeDesignations ?? this.employeeDesignations,
      employeeDesignationStatusUI: employeeDesignationStatusUI ?? this.employeeDesignationStatusUI,
      loadedEmployeeDesignation: loadedEmployeeDesignation ?? this.loadedEmployeeDesignation,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      designation: designation ?? this.designation,
      description: description ?? this.description,
      designationDate: designationDate ?? this.designationDate,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [employeeDesignations, employeeDesignationStatusUI,
     loadedEmployeeDesignation, editMode, deleteStatus, formStatus, generalNotificationKey, 
designation,description,designationDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
