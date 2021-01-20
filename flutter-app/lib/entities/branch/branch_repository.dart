import 'package:amcCarePlanner/entities/branch/branch_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class BranchRepository {
    BranchRepository();
  
  static final String uriEndpoint = '/branches';

  Future<List<Branch>> getAllBranches() async {
    final allBranchesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Branch>>(allBranchesRequest.body);
  }

  Future<Branch> getBranch(int id) async {
    final branchRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Branch>(branchRequest.body);
  }

  Future<Branch> create(Branch branch) async {
    final branchRequest = await HttpUtils.postRequest('$uriEndpoint', branch);
    return JsonMapper.deserialize<Branch>(branchRequest.body);
  }

  Future<Branch> update(Branch branch) async {
    final branchRequest = await HttpUtils.putRequest('$uriEndpoint', branch);
    return JsonMapper.deserialize<Branch>(branchRequest.body);
  }

  Future<void> delete(int id) async {
    final branchRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
