import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './eligibility.reducer';
import { IEligibility } from 'app/shared/model/eligibility.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEligibilityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EligibilityDetail = (props: IEligibilityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { eligibilityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.eligibility.detail.title">Eligibility</Translate> [<b>{eligibilityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.eligibility.note">Note</Translate>
            </span>
          </dt>
          <dd>{eligibilityEntity.note}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.eligibility.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {eligibilityEntity.lastUpdatedDate ? (
              <TextFormat value={eligibilityEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.eligibility.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{eligibilityEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.eligibility.eligibilityType">Eligibility Type</Translate>
          </dt>
          <dd>{eligibilityEntity.eligibilityTypeEligibilityType ? eligibilityEntity.eligibilityTypeEligibilityType : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.eligibility.employee">Employee</Translate>
          </dt>
          <dd>{eligibilityEntity.employeeEmployeeCode ? eligibilityEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/eligibility" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/eligibility/${eligibilityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ eligibility }: IRootState) => ({
  eligibilityEntity: eligibility.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EligibilityDetail);
