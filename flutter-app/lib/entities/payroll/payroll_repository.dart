import 'package:amcCarePlanner/entities/payroll/payroll_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class PayrollRepository {
    PayrollRepository();
  
  static final String uriEndpoint = '/payrolls';

  Future<List<Payroll>> getAllPayrolls() async {
    final allPayrollsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Payroll>>(allPayrollsRequest.body);
  }

  Future<Payroll> getPayroll(int id) async {
    final payrollRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Payroll>(payrollRequest.body);
  }

  Future<Payroll> create(Payroll payroll) async {
    final payrollRequest = await HttpUtils.postRequest('$uriEndpoint', payroll);
    return JsonMapper.deserialize<Payroll>(payrollRequest.body);
  }

  Future<Payroll> update(Payroll payroll) async {
    final payrollRequest = await HttpUtils.putRequest('$uriEndpoint', payroll);
    return JsonMapper.deserialize<Payroll>(payrollRequest.body);
  }

  Future<void> delete(int id) async {
    final payrollRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
