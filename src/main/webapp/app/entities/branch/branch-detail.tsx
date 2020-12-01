import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './branch.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBranchDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BranchDetail = (props: IBranchDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { branchEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.branch.detail.title">Branch</Translate> [<b>{branchEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="carePlannerApp.branch.name">Name</Translate>
            </span>
          </dt>
          <dd>{branchEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="carePlannerApp.branch.address">Address</Translate>
            </span>
          </dt>
          <dd>{branchEntity.address}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="carePlannerApp.branch.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{branchEntity.telephone}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.branch.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {branchEntity.lastUpdatedDate ? <TextFormat value={branchEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="carePlannerApp.branch.client">Client</Translate>
          </dt>
          <dd>{branchEntity.clientClientName ? branchEntity.clientClientName : ''}</dd>
        </dl>
        <Button tag={Link} to="/branch" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/branch/${branchEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ branch }: IRootState) => ({
  branchEntity: branch.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BranchDetail);
