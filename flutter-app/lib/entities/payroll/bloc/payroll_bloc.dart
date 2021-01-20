import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/payroll/payroll_model.dart';
import 'package:amcCarePlanner/entities/payroll/payroll_repository.dart';
import 'package:amcCarePlanner/entities/payroll/bloc/payroll_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'payroll_events.dart';
part 'payroll_state.dart';

class PayrollBloc extends Bloc<PayrollEvent, PayrollState> {
  final PayrollRepository _payrollRepository;

  final paymentDateController = TextEditingController();
  final payPeriodController = TextEditingController();
  final totalHoursWorkedController = TextEditingController();
  final grossPayController = TextEditingController();
  final netPayController = TextEditingController();
  final totalTaxController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  PayrollBloc({@required PayrollRepository payrollRepository}) : assert(payrollRepository != null),
        _payrollRepository = payrollRepository, 
  super(PayrollState(null,null,null,));

  @override
  void onTransition(Transition<PayrollEvent, PayrollState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<PayrollState> mapEventToState(PayrollEvent event) async* {
    if (event is InitPayrollList) {
      yield* onInitList(event);
    } else if (event is PayrollFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadPayrollByIdForEdit) {
      yield* onLoadPayrollIdForEdit(event);
    } else if (event is DeletePayrollById) {
      yield* onDeletePayrollId(event);
    } else if (event is LoadPayrollByIdForView) {
      yield* onLoadPayrollIdForView(event);
    }else if (event is PaymentDateChanged){
      yield* onPaymentDateChange(event);
    }else if (event is PayPeriodChanged){
      yield* onPayPeriodChange(event);
    }else if (event is TotalHoursWorkedChanged){
      yield* onTotalHoursWorkedChange(event);
    }else if (event is GrossPayChanged){
      yield* onGrossPayChange(event);
    }else if (event is NetPayChanged){
      yield* onNetPayChange(event);
    }else if (event is TotalTaxChanged){
      yield* onTotalTaxChange(event);
    }else if (event is PayrollStatusChanged){
      yield* onPayrollStatusChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<PayrollState> onInitList(InitPayrollList event) async* {
    yield this.state.copyWith(payrollStatusUI: PayrollStatusUI.loading);
    List<Payroll> payrolls = await _payrollRepository.getAllPayrolls();
    yield this.state.copyWith(payrolls: payrolls, payrollStatusUI: PayrollStatusUI.done);
  }

  Stream<PayrollState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Payroll result;
        if(this.state.editMode) {
          Payroll newPayroll = Payroll(state.loadedPayroll.id,
            this.state.paymentDate.value,  
            this.state.payPeriod.value,  
            this.state.totalHoursWorked.value,  
            this.state.grossPay.value,  
            this.state.netPay.value,  
            this.state.totalTax.value,  
            this.state.payrollStatus.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _payrollRepository.update(newPayroll);
        } else {
          Payroll newPayroll = Payroll(null,
            this.state.paymentDate.value,  
            this.state.payPeriod.value,  
            this.state.totalHoursWorked.value,  
            this.state.grossPay.value,  
            this.state.netPay.value,  
            this.state.totalTax.value,  
            this.state.payrollStatus.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _payrollRepository.create(newPayroll);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<PayrollState> onLoadPayrollIdForEdit(LoadPayrollByIdForEdit event) async* {
    yield this.state.copyWith(payrollStatusUI: PayrollStatusUI.loading);
    Payroll loadedPayroll = await _payrollRepository.getPayroll(event.id);

    final paymentDate = PaymentDateInput.dirty(loadedPayroll?.paymentDate != null ? loadedPayroll.paymentDate: null);
    final payPeriod = PayPeriodInput.dirty(loadedPayroll?.payPeriod != null ? loadedPayroll.payPeriod: '');
    final totalHoursWorked = TotalHoursWorkedInput.dirty(loadedPayroll?.totalHoursWorked != null ? loadedPayroll.totalHoursWorked: 0);
    final grossPay = GrossPayInput.dirty(loadedPayroll?.grossPay != null ? loadedPayroll.grossPay: '');
    final netPay = NetPayInput.dirty(loadedPayroll?.netPay != null ? loadedPayroll.netPay: '');
    final totalTax = TotalTaxInput.dirty(loadedPayroll?.totalTax != null ? loadedPayroll.totalTax: '');
    final payrollStatus = PayrollStatusInput.dirty(loadedPayroll?.payrollStatus != null ? loadedPayroll.payrollStatus: null);
    final createdDate = CreatedDateInput.dirty(loadedPayroll?.createdDate != null ? loadedPayroll.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedPayroll?.lastUpdatedDate != null ? loadedPayroll.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedPayroll?.clientId != null ? loadedPayroll.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedPayroll?.hasExtraData != null ? loadedPayroll.hasExtraData: false);

    yield this.state.copyWith(loadedPayroll: loadedPayroll, editMode: true,
      paymentDate: paymentDate,
      payPeriod: payPeriod,
      totalHoursWorked: totalHoursWorked,
      grossPay: grossPay,
      netPay: netPay,
      totalTax: totalTax,
      payrollStatus: payrollStatus,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    payrollStatusUI: PayrollStatusUI.done);

    paymentDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedPayroll?.paymentDate);
    payPeriodController.text = loadedPayroll.payPeriod;
    totalHoursWorkedController.text = loadedPayroll.totalHoursWorked.toString();
    grossPayController.text = loadedPayroll.grossPay;
    netPayController.text = loadedPayroll.netPay;
    totalTaxController.text = loadedPayroll.totalTax;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedPayroll?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedPayroll?.lastUpdatedDate);
    clientIdController.text = loadedPayroll.clientId.toString();
  }

  Stream<PayrollState> onDeletePayrollId(DeletePayrollById event) async* {
    try {
      await _payrollRepository.delete(event.id);
      this.add(InitPayrollList());
      yield this.state.copyWith(deleteStatus: PayrollDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: PayrollDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: PayrollDeleteStatus.none);
  }

  Stream<PayrollState> onLoadPayrollIdForView(LoadPayrollByIdForView event) async* {
    yield this.state.copyWith(payrollStatusUI: PayrollStatusUI.loading);
    try {
      Payroll loadedPayroll = await _payrollRepository.getPayroll(event.id);
      yield this.state.copyWith(loadedPayroll: loadedPayroll, payrollStatusUI: PayrollStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedPayroll: null, payrollStatusUI: PayrollStatusUI.error);
    }
  }


  Stream<PayrollState> onPaymentDateChange(PaymentDateChanged event) async* {
    final paymentDate = PaymentDateInput.dirty(event.paymentDate);
    yield this.state.copyWith(
      paymentDate: paymentDate,
      formStatus: Formz.validate([
        paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onPayPeriodChange(PayPeriodChanged event) async* {
    final payPeriod = PayPeriodInput.dirty(event.payPeriod);
    yield this.state.copyWith(
      payPeriod: payPeriod,
      formStatus: Formz.validate([
      this.state.paymentDate,
        payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onTotalHoursWorkedChange(TotalHoursWorkedChanged event) async* {
    final totalHoursWorked = TotalHoursWorkedInput.dirty(event.totalHoursWorked);
    yield this.state.copyWith(
      totalHoursWorked: totalHoursWorked,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
        totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onGrossPayChange(GrossPayChanged event) async* {
    final grossPay = GrossPayInput.dirty(event.grossPay);
    yield this.state.copyWith(
      grossPay: grossPay,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
        grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onNetPayChange(NetPayChanged event) async* {
    final netPay = NetPayInput.dirty(event.netPay);
    yield this.state.copyWith(
      netPay: netPay,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
        netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onTotalTaxChange(TotalTaxChanged event) async* {
    final totalTax = TotalTaxInput.dirty(event.totalTax);
    yield this.state.copyWith(
      totalTax: totalTax,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
        totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onPayrollStatusChange(PayrollStatusChanged event) async* {
    final payrollStatus = PayrollStatusInput.dirty(event.payrollStatus);
    yield this.state.copyWith(
      payrollStatus: payrollStatus,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
        payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<PayrollState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.paymentDate,
      this.state.payPeriod,
      this.state.totalHoursWorked,
      this.state.grossPay,
      this.state.netPay,
      this.state.totalTax,
      this.state.payrollStatus,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    paymentDateController.dispose();
    payPeriodController.dispose();
    totalHoursWorkedController.dispose();
    grossPayController.dispose();
    netPayController.dispose();
    totalTaxController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}