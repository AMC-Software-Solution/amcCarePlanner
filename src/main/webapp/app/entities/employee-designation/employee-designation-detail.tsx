import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-designation.reducer';
import { IEmployeeDesignation } from 'app/shared/model/employee-designation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDesignationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDesignationDetail = (props: IEmployeeDesignationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeDesignationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employeeDesignation.detail.title">EmployeeDesignation</Translate> [
          <b>{employeeDesignationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="designation">
              <Translate contentKey="carePlannerApp.employeeDesignation.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{employeeDesignationEntity.designation}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.employeeDesignation.description">Description</Translate>
            </span>
          </dt>
          <dd>{employeeDesignationEntity.description}</dd>
          <dt>
            <span id="designationDate">
              <Translate contentKey="carePlannerApp.employeeDesignation.designationDate">Designation Date</Translate>
            </span>
          </dt>
          <dd>{employeeDesignationEntity.designationDate}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.employeeDesignation.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{employeeDesignationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.employeeDesignation.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{employeeDesignationEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/employee-designation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-designation/${employeeDesignationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeDesignation }: IRootState) => ({
  employeeDesignationEntity: employeeDesignation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDesignationDetail);
