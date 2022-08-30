package datastructs.Graph;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnionFindTest {

    UnionFind unionFind;

    @BeforeEach
    public void setup() {
        unionFind = new UnionFind(10);
    }

    @Test
    public void testFind_ReturnsRoot() {
        // Arrange
        int[] parents = new int[]{0, 2, 2, 1, 4};
        int[] rank = new int[]{0, 1, 2, 0, 1};
        unionFind = new UnionFind(parents, rank);

        // Act
        int root = unionFind.find(1);

        // Assert
        assertThat(root, is(2));
    }

    @Test
    public void testFind_CompressesPaths() {
        // Arrange
        int[] parents = new int[]{0, 2, 2, 1, 4};
        int[] rank = new int[]{0, 1, 2, 0, 1};
        unionFind = new UnionFind(parents, rank);

        int[] expectedParents = new int[]{0, 2, 2, 2, 4};

        // Act
        int root = unionFind.find(3);

        // Assert
        assertThat(root, is(2));
        assertThat(parents, is(expectedParents));
    }

    @Test
    public void testUnion_ReturnsFalseWhenAlreadyMerged () {
        // Arrange
        int[] parents = new int[]{0, 2, 2, 1, 4};
        int[] rank = new int[]{0, 1, 2, 0, 1};
        unionFind = new UnionFind(parents, rank);

        // Act
        boolean merged = unionFind.union(1, 2);

        // Assert
        assertThat(merged, is(false));
    }

    @Test
    public void testUnion_ReturnsTrueAndIncrementsRankWhenSameRank() {
        // Arrange
        int[] parents = new int[]{0, 1, 2, 1, 2};
        int[] rank = new int[]{0, 1, 1, 0, 0};
        unionFind = new UnionFind(parents, rank);

        int[] expectedParents = new int[]{0, 1, 1, 1, 2};
        int[] expectedRank = new int[]{0, 2, 1, 0, 0};

        // Act
        boolean merged = unionFind.union(1, 2);

        // Assert
        assertThat(merged, is(true));
        assertThat(expectedParents, is(parents));
        assertThat(expectedRank, is(rank));
    }

    @Test
    public void testUnion_ReturnsTrueAndDoesNotIncrementRankWhenDifferentRanks() {
        // Arrange
        int[] parents = new int[]{0, 1, 2, 2, 3};
        int[] rank = new int[]{0, 1, 2, 0, 0};
        unionFind = new UnionFind(parents, rank);

        int[] expectedParents = new int[]{0, 2, 2, 2, 3};
        int[] expectedRank = new int[]{0, 1, 2, 0, 0};

        // Act
        boolean merged = unionFind.union(1, 2);

        // Assert
        assertThat(merged, is(true));
        assertThat(expectedParents, is(parents));
        assertThat(expectedRank, is(rank));
    }
}
