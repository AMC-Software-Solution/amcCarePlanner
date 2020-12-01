import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './disability.reducer';
import { IDisability } from 'app/shared/model/disability.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDisabilityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DisabilityDetail = (props: IDisabilityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { disabilityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.disability.detail.title">Disability</Translate> [<b>{disabilityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.disability.note">Note</Translate>
            </span>
          </dt>
          <dd>{disabilityEntity.note}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.disability.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {disabilityEntity.lastUpdatedDate ? (
              <TextFormat value={disabilityEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.disability.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{disabilityEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.disability.disabilityType">Disability Type</Translate>
          </dt>
          <dd>{disabilityEntity.disabilityTypeDisability ? disabilityEntity.disabilityTypeDisability : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.disability.employee">Employee</Translate>
          </dt>
          <dd>{disabilityEntity.employeeEmployeeCode ? disabilityEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/disability" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disability/${disabilityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ disability }: IRootState) => ({
  disabilityEntity: disability.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DisabilityDetail);
