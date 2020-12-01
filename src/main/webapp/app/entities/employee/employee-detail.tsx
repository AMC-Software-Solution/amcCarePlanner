import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDetail = (props: IEmployeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employee.detail.title">Employee</Translate> [<b>{employeeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="carePlannerApp.employee.title">Title</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.title}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="carePlannerApp.employee.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.firstName}</dd>
          <dt>
            <span id="middleInitial">
              <Translate contentKey="carePlannerApp.employee.middleInitial">Middle Initial</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.middleInitial}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="carePlannerApp.employee.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastName}</dd>
          <dt>
            <span id="preferredName">
              <Translate contentKey="carePlannerApp.employee.preferredName">Preferred Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.preferredName}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="carePlannerApp.employee.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.gender}</dd>
          <dt>
            <span id="employeeCode">
              <Translate contentKey="carePlannerApp.employee.employeeCode">Employee Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.employeeCode}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="carePlannerApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="nationalInsuranceNumber">
              <Translate contentKey="carePlannerApp.employee.nationalInsuranceNumber">National Insurance Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.nationalInsuranceNumber}</dd>
          <dt>
            <span id="employeeContractType">
              <Translate contentKey="carePlannerApp.employee.employeeContractType">Employee Contract Type</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.employeeContractType}</dd>
          <dt>
            <span id="pinCode">
              <Translate contentKey="carePlannerApp.employee.pinCode">Pin Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.pinCode}</dd>
          <dt>
            <span id="transportMode">
              <Translate contentKey="carePlannerApp.employee.transportMode">Transport Mode</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.transportMode}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="carePlannerApp.employee.address">Address</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.address}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="carePlannerApp.employee.county">County</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.county}</dd>
          <dt>
            <span id="postCode">
              <Translate contentKey="carePlannerApp.employee.postCode">Post Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.postCode}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="carePlannerApp.employee.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.dateOfBirth ? (
              <TextFormat value={employeeEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="photo">
              <Translate contentKey="carePlannerApp.employee.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.photo ? (
              <div>
                {employeeEntity.photoContentType ? (
                  <a onClick={openFile(employeeEntity.photoContentType, employeeEntity.photo)}>
                    <img src={`data:${employeeEntity.photoContentType};base64,${employeeEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {employeeEntity.photoContentType}, {byteSize(employeeEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="photoUrl">
              <Translate contentKey="carePlannerApp.employee.photoUrl">Photo Url</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.photoUrl}</dd>
          <dt>
            <span id="acruedHolidayHours">
              <Translate contentKey="carePlannerApp.employee.acruedHolidayHours">Acrued Holiday Hours</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.acruedHolidayHours}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.employee.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeEntity.lastUpdatedDate ? (
              <TextFormat value={employeeEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.employee.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employee.user">User</Translate>
          </dt>
          <dd>{employeeEntity.userLogin ? employeeEntity.userLogin : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employee.nationality">Nationality</Translate>
          </dt>
          <dd>{employeeEntity.nationalityCountryName ? employeeEntity.nationalityCountryName : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);
