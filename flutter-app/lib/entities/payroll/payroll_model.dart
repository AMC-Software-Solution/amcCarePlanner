import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';
import '../timesheet/timesheet_model.dart';

@jsonSerializable
class Payroll {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'paymentDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime paymentDate;

  @JsonProperty(name: 'payPeriod')
  final String payPeriod;

  @JsonProperty(name: 'totalHoursWorked')
  final int totalHoursWorked;

  @JsonProperty(name: 'grossPay')
  final String grossPay;

  @JsonProperty(name: 'netPay')
  final String netPay;

  @JsonProperty(name: 'totalTax')
  final String totalTax;

  @JsonProperty(name: 'payrollStatus', enumValues: PayrollStatus.values)
  final PayrollStatus payrollStatus;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'employee')
  final Employee employee;

  @JsonProperty(name: 'timesheet')
  final Timesheet timesheet;
        
 const Payroll (
     this.id,
        this.paymentDate,
        this.payPeriod,
        this.totalHoursWorked,
        this.grossPay,
        this.netPay,
        this.totalTax,
        this.payrollStatus,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.employee,
        this.timesheet,
    );

@override
String toString() {
    return 'Payroll{'+
    'id: $id,' +
        'paymentDate: $paymentDate,' +
        'payPeriod: $payPeriod,' +
        'totalHoursWorked: $totalHoursWorked,' +
        'grossPay: $grossPay,' +
        'netPay: $netPay,' +
        'totalTax: $totalTax,' +
        'payrollStatus: $payrollStatus,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'employee: $employee,' +
        'timesheet: $timesheet,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Payroll &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: PayrollStatus.values)
enum PayrollStatus {
    CREATED ,
    PROCESSING ,
    PAID 
}