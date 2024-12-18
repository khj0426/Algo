package boj_15683;

import java.io.*;
import java.util.*;

class Node {
    private int num;
    private int x;
    private int y;

    public Node(int num, int x, int y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }

    public int getNum() {
        return this.num;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

public class Main {
    public static int n, m;
    public static int[][] map;
    public static int[][] copyMap;
    public static int[] dirArray;
    public static ArrayList<Node> cctvList = new ArrayList<>();
    public static int[] dx = {-1, 1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};
    public static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != 0 && map[i][j] != 6) {
                    cctvList.add(new Node(map[i][j], i, j));
                }
            }
        }

        dirArray = new int[cctvList.size()];
        dfs(0, cctvList.size());

        System.out.println(answer);
    }

    public static void dfs(int depth, int r) {
        if (depth == r) {
            copyMap = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    copyMap[i][j] = map[i][j];
                }
            }

            for (int i = 0; i < cctvList.size(); i++) {
                Node cctv = cctvList.get(i);
                int dir = dirArray[i];
                watch(cctv, dir);
            }

            getBlindSpot();

            return;
        }

        for (int i = 0; i < 4; i++) {
            dirArray[depth] = i;
            dfs(depth + 1, r);
        }
    }

    public static void watch(Node cctv, int dir) {
        if (cctv.getNum() == 1) {
            if (dir == 0) bfs(cctv, 0);
            else if (dir == 1) bfs(cctv, 1);
            else if (dir == 2) bfs(cctv, 2);
            else bfs(cctv, 3);
        } else if (cctv.getNum() == 2) {
            if (dir == 0 || dir == 1) {
                bfs(cctv, 0);
                bfs(cctv, 1);
            } else {
                bfs(cctv, 2);
                bfs(cctv, 3);
            }
        } else if (cctv.getNum() == 3) {
            if (dir == 0) {
                bfs(cctv, 0);
                bfs(cctv, 3);
            } else if (dir == 1) {
                bfs(cctv, 0);
                bfs(cctv, 2);
            } else if (dir == 2) {
                bfs(cctv, 1);
                bfs(cctv, 3);
            } else {
                bfs(cctv, 1);
                bfs(cctv, 2);
            }
        } else if (cctv.getNum() == 4) {
            if (dir == 0) {
                bfs(cctv, 0);
                bfs(cctv, 2);
                bfs(cctv, 3);
            } else if (dir == 1) {
                bfs(cctv, 0);
                bfs(cctv, 1);
                bfs(cctv, 2);
            } else if (dir == 2) {
                bfs(cctv, 1);
                bfs(cctv, 2);
                bfs(cctv, 3);
            } else {
                bfs(cctv, 0);
                bfs(cctv, 1);
                bfs(cctv, 3);
            }
        } else {
            bfs(cctv, 0);
            bfs(cctv, 1);
            bfs(cctv, 2);
            bfs(cctv, 3);
        }
    }

    public static void bfs(Node start, int d) {
        Queue<Node> q = new LinkedList<>();
        q.offer(start);
        while (!q.isEmpty()) {
            Node now = q.poll();
            int nx = now.getX() + dx[d];
            int ny = now.getY() + dy[d];
            if (nx >= 0 && ny >=0 && nx < n && ny < m) {
                if (copyMap[nx][ny] != 6) {
                    q.offer(new Node(now.getNum(), nx, ny));
                    if (copyMap[nx][ny] == 0) {
                        copyMap[nx][ny] = -1;
                    }
                }
            }
        }
    }

    public static void getBlindSpot() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (copyMap[i][j] == 0) {
                    count += 1;
                }
            }
        }
        answer = Math.min(answer, count);
    }
}
