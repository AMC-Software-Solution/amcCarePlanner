part of 'payroll_bloc.dart';

abstract class PayrollEvent extends Equatable {
  const PayrollEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitPayrollList extends PayrollEvent {}

class PaymentDateChanged extends PayrollEvent {
  final DateTime paymentDate;
  
  const PaymentDateChanged({@required this.paymentDate});
  
  @override
  List<Object> get props => [paymentDate];
}
class PayPeriodChanged extends PayrollEvent {
  final String payPeriod;
  
  const PayPeriodChanged({@required this.payPeriod});
  
  @override
  List<Object> get props => [payPeriod];
}
class TotalHoursWorkedChanged extends PayrollEvent {
  final int totalHoursWorked;
  
  const TotalHoursWorkedChanged({@required this.totalHoursWorked});
  
  @override
  List<Object> get props => [totalHoursWorked];
}
class GrossPayChanged extends PayrollEvent {
  final String grossPay;
  
  const GrossPayChanged({@required this.grossPay});
  
  @override
  List<Object> get props => [grossPay];
}
class NetPayChanged extends PayrollEvent {
  final String netPay;
  
  const NetPayChanged({@required this.netPay});
  
  @override
  List<Object> get props => [netPay];
}
class TotalTaxChanged extends PayrollEvent {
  final String totalTax;
  
  const TotalTaxChanged({@required this.totalTax});
  
  @override
  List<Object> get props => [totalTax];
}
class PayrollStatusChanged extends PayrollEvent {
  final PayrollStatus payrollStatus;
  
  const PayrollStatusChanged({@required this.payrollStatus});
  
  @override
  List<Object> get props => [payrollStatus];
}
class CreatedDateChanged extends PayrollEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends PayrollEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends PayrollEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends PayrollEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class PayrollFormSubmitted extends PayrollEvent {}

class LoadPayrollByIdForEdit extends PayrollEvent {
  final int id;

  const LoadPayrollByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeletePayrollById extends PayrollEvent {
  final int id;

  const DeletePayrollById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadPayrollByIdForView extends PayrollEvent {
  final int id;

  const LoadPayrollByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
