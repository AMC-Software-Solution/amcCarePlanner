import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './carer-service-user-relation.reducer';
import { ICarerServiceUserRelation } from 'app/shared/model/carer-service-user-relation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICarerServiceUserRelationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CarerServiceUserRelation = (props: ICarerServiceUserRelationProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { carerServiceUserRelationList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="carer-service-user-relation-heading">
        <Translate contentKey="carePlannerApp.carerServiceUserRelation.home.title">Carer Service User Relations</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.carerServiceUserRelation.home.createLabel">
            Create new Carer Service User Relation
          </Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {carerServiceUserRelationList && carerServiceUserRelationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reason')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.reason">Reason</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('count')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.count">Count</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('clientId')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.clientId">Client Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('hasExtraData')}>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.hasExtraData">Has Extra Data</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.relationType">Relation Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.employee">Employee</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.serviceUser">Service User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {carerServiceUserRelationList.map((carerServiceUserRelation, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${carerServiceUserRelation.id}`} color="link" size="sm">
                      {carerServiceUserRelation.id}
                    </Button>
                  </td>
                  <td>{carerServiceUserRelation.reason}</td>
                  <td>{carerServiceUserRelation.count}</td>
                  <td>
                    {carerServiceUserRelation.createdDate ? (
                      <TextFormat type="date" value={carerServiceUserRelation.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {carerServiceUserRelation.lastUpdatedDate ? (
                      <TextFormat type="date" value={carerServiceUserRelation.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{carerServiceUserRelation.clientId}</td>
                  <td>{carerServiceUserRelation.hasExtraData ? 'true' : 'false'}</td>
                  <td>
                    {carerServiceUserRelation.relationTypeRelationType ? (
                      <Link to={`relationship-type/${carerServiceUserRelation.relationTypeId}`}>
                        {carerServiceUserRelation.relationTypeRelationType}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {carerServiceUserRelation.employeeEmployeeCode ? (
                      <Link to={`employee/${carerServiceUserRelation.employeeId}`}>{carerServiceUserRelation.employeeEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {carerServiceUserRelation.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${carerServiceUserRelation.serviceUserId}`}>
                        {carerServiceUserRelation.serviceUserServiceUserCode}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${carerServiceUserRelation.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${carerServiceUserRelation.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${carerServiceUserRelation.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.home.notFound">
                No Carer Service User Relations found
              </Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={carerServiceUserRelationList && carerServiceUserRelationList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ carerServiceUserRelation }: IRootState) => ({
  carerServiceUserRelationList: carerServiceUserRelation.entities,
  loading: carerServiceUserRelation.loading,
  totalItems: carerServiceUserRelation.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerServiceUserRelation);
