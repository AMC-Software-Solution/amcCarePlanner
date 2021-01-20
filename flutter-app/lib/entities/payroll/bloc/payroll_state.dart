part of 'payroll_bloc.dart';

enum PayrollStatusUI {init, loading, error, done}
enum PayrollDeleteStatus {ok, ko, none}

class PayrollState extends Equatable {
  final List<Payroll> payrolls;
  final Payroll loadedPayroll;
  final bool editMode;
  final PayrollDeleteStatus deleteStatus;
  final PayrollStatusUI payrollStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final PaymentDateInput paymentDate;
  final PayPeriodInput payPeriod;
  final TotalHoursWorkedInput totalHoursWorked;
  final GrossPayInput grossPay;
  final NetPayInput netPay;
  final TotalTaxInput totalTax;
  final PayrollStatusInput payrollStatus;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  PayrollState(
PaymentDateInput paymentDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.payrolls = const [],
    this.payrollStatusUI = PayrollStatusUI.init,
    this.loadedPayroll = const Payroll(0,null,'',0,'','','',null,null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = PayrollDeleteStatus.none,
    this.payPeriod = const PayPeriodInput.pure(),
    this.totalHoursWorked = const TotalHoursWorkedInput.pure(),
    this.grossPay = const GrossPayInput.pure(),
    this.netPay = const NetPayInput.pure(),
    this.totalTax = const TotalTaxInput.pure(),
    this.payrollStatus = const PayrollStatusInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.paymentDate = paymentDate ?? PaymentDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  PayrollState copyWith({
    List<Payroll> payrolls,
    PayrollStatusUI payrollStatusUI,
    bool editMode,
    PayrollDeleteStatus deleteStatus,
    Payroll loadedPayroll,
    FormzStatus formStatus,
    String generalNotificationKey,
    PaymentDateInput paymentDate,
    PayPeriodInput payPeriod,
    TotalHoursWorkedInput totalHoursWorked,
    GrossPayInput grossPay,
    NetPayInput netPay,
    TotalTaxInput totalTax,
    PayrollStatusInput payrollStatus,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return PayrollState(
        paymentDate,
        createdDate,
        lastUpdatedDate,
      payrolls: payrolls ?? this.payrolls,
      payrollStatusUI: payrollStatusUI ?? this.payrollStatusUI,
      loadedPayroll: loadedPayroll ?? this.loadedPayroll,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      payPeriod: payPeriod ?? this.payPeriod,
      totalHoursWorked: totalHoursWorked ?? this.totalHoursWorked,
      grossPay: grossPay ?? this.grossPay,
      netPay: netPay ?? this.netPay,
      totalTax: totalTax ?? this.totalTax,
      payrollStatus: payrollStatus ?? this.payrollStatus,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [payrolls, payrollStatusUI,
     loadedPayroll, editMode, deleteStatus, formStatus, generalNotificationKey, 
paymentDate,payPeriod,totalHoursWorked,grossPay,netPay,totalTax,payrollStatus,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
