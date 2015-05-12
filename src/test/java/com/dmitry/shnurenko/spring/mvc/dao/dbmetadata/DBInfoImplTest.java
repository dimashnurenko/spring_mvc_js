package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.TEST;
import static junit.framework.Assert.assertEquals;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class DBInfoImplTest {

    private static final String PATH_TO_CORRECT_QUERIES        = "/correctQueries.txt";
    private static final String QUERY_WITHOUT_SPECIAL_SYMBOL   = "/inCorrectQuery.txt";
    private static final String QUERY_WITHOUT_SPECIAL_SYMBOL_2 = "/inCorrectQuery2.txt";

    private DBInfoImpl dbInfo;

    @Before
    public void setUp() {
        dbInfo = new DBInfoImpl();
    }

    @Test
    public void queryShouldBeGot() throws Exception {
        dbInfo.readQueriesFromFile(PATH_TO_CORRECT_QUERIES);

        String query = dbInfo.getQuery(TEST);

        assertEquals(query, "value1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionShouldBeThrownWhenQueryIncorrectEnds() throws Exception {
        dbInfo.readQueriesFromFile(QUERY_WITHOUT_SPECIAL_SYMBOL);

        dbInfo.getQuery(TEST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionShouldBeThrownWhenQueryDoesNotContainSpecialSymbol() throws Exception {
        dbInfo.readQueriesFromFile(QUERY_WITHOUT_SPECIAL_SYMBOL_2);

        dbInfo.getQuery(TEST);
    }
}