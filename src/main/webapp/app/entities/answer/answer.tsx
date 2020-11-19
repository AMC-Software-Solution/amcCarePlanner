import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IAnswerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Answer = (props: IAnswerProps) => {
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

  const { answerList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="answer-heading">
        <Translate contentKey="carePlannerApp.answer.home.title">Answers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.answer.home.createLabel">Create new Answer</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {answerList && answerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('answer')}>
                  <Translate contentKey="carePlannerApp.answer.answer">Answer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="carePlannerApp.answer.description">Description</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attribute1')}>
                  <Translate contentKey="carePlannerApp.answer.attribute1">Attribute 1</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attribute2')}>
                  <Translate contentKey="carePlannerApp.answer.attribute2">Attribute 2</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attribute3')}>
                  <Translate contentKey="carePlannerApp.answer.attribute3">Attribute 3</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attribute4')}>
                  <Translate contentKey="carePlannerApp.answer.attribute4">Attribute 4</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('attribute5')}>
                  <Translate contentKey="carePlannerApp.answer.attribute5">Attribute 5</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="carePlannerApp.answer.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.answer.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantId')}>
                  <Translate contentKey="carePlannerApp.answer.tenantId">Tenant Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.answer.question">Question</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.answer.serviceUser">Service User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.answer.recordedBy">Recorded By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {answerList.map((answer, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${answer.id}`} color="link" size="sm">
                      {answer.id}
                    </Button>
                  </td>
                  <td>{answer.answer}</td>
                  <td>{answer.description}</td>
                  <td>{answer.attribute1}</td>
                  <td>{answer.attribute2}</td>
                  <td>{answer.attribute3}</td>
                  <td>{answer.attribute4}</td>
                  <td>{answer.attribute5}</td>
                  <td>{answer.createdDate ? <TextFormat type="date" value={answer.createdDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {answer.lastUpdatedDate ? <TextFormat type="date" value={answer.lastUpdatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{answer.tenantId}</td>
                  <td>{answer.questionQuestion ? <Link to={`question/${answer.questionId}`}>{answer.questionQuestion}</Link> : ''}</td>
                  <td>
                    {answer.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${answer.serviceUserId}`}>{answer.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {answer.recordedByEmployeeCode ? (
                      <Link to={`employee/${answer.recordedById}`}>{answer.recordedByEmployeeCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${answer.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${answer.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${answer.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="carePlannerApp.answer.home.notFound">No Answers found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={answerList && answerList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ answer }: IRootState) => ({
  answerList: answer.entities,
  loading: answer.loading,
  totalItems: answer.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Answer);
