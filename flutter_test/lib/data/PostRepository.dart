import 'package:test1/data/Post.dart';

class PostRepository {
  final List<Post> _posts = [Post(content: "test")]; // Dummy list of posts

  // Fetch posts from the API or any other data source
  Future<List<Post>> fetchPosts() async {
    // await Future.delayed(Duration(seconds: 2)); // Simulating delay

    // Return a copy of the posts
    return List<Post>.from(_posts);
  }

  // Add a new post
  Future<void> addPost(Post post) async {
    await Future.delayed(Duration(seconds: 2)); // Simulating delay

    _posts.add(post);
  }
}
