package n14_hash_table;

public class ValidSudo {
    // 36. 有效的数独
    // 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
    //数字 1-9 在每一行只能出现一次。
    //数字 1-9 在每一列只能出现一次。
    //数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）

    public boolean isValidSudoku(char[][] board) {
        int[] row = new int[9];
        int[] col = new int[9];
        int[][] cell = new int[3][3];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 注意 1：'.' 的时候跳过
                if (board[i][j] == '.') {
                    continue;
                }
                // 注意 2：这里转换成整型数值 0 - 8
                int index = board[i][j] - '1';
                if ((((row[i] >> index) & 1) == 1) ||
                        (((col[j] >> index) & 1) == 1) ||
                        (((cell[i / 3][j / 3] >> index) & 1) == 1)) {
                    return false;
                }
                row[i] ^= 1 << index;
                col[j] ^= 1 << index;
                cell[i / 3][j / 3] ^= 1 << index;
            }
        }
        return true;
    }
}
